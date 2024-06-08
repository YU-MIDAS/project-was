package com.example.demo.bounded_context.solution.service;

import com.example.demo.base.exception.CustomException;
import com.example.demo.base.exception.ExceptionCode;
import com.example.demo.bounded_context.account.entity.Account;
import com.example.demo.bounded_context.account.service.AccountService;
import com.example.demo.bounded_context.solution.dto.DetectRequest;
import com.example.demo.bounded_context.solution.dto.DetectResponse;
import com.example.demo.bounded_context.solution.dto.SolutionResponse;
import com.example.demo.bounded_context.solution.dto.ContributeCreationListResponse;
import com.example.demo.bounded_context.solution.dto.SolutionRequest;
import com.example.demo.bounded_context.solution.dto.SolutionListResponse;
import com.example.demo.bounded_context.solution.entity.ContributedCreationState;
import com.example.demo.bounded_context.solution.entity.Waste;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.net.URLDecoder;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class SolutionUseCase {
    private final AccountService accountService;
    private final WasteService wasteService;
    private final TagService tagService;
    private final DetectService detectService;

    /**
     * 솔루션 이미지 검색
     * - 모든 사용자는 이미지를 사용하여 솔루션 검색을 할 수 있다.
     * - 카테고리의 확률과 물체 좌표를 출력한다.
     */
    public DetectResponse searchByImage(String imageUrl){
        log.info("솔루션 이미지 검색");
        DetectRequest request = DetectRequest.fromEntity(imageUrl);
        DetectResponse detectResponse = detectService.imageDetection(request);
        return detectResponse;
    }

    /**
     * 솔루션 키워드 검색
     * - 모든 사용자는 키워드를 사용하여 솔루션 검색을 할 수 있다.
     * - 솔루션 이름 / 태그 이름 순으로 검색하여 솔루션을 검색할 수 있다.
     * - 승인 되지 않은 솔루션은 검색되지 않는다.
     */
    public SolutionResponse searchByKeyword(String wasteName){
        log.info("솔루션 키워드 검색");
        Long wasteId = wasteService.findByName(wasteName)
                .or(() -> tagService.findIdByName(wasteName))
                .orElseThrow(() -> new CustomException(ExceptionCode.WASTE_NOT_FOUND));

        Waste foundSolution = wasteService.findFetchById(wasteId);
        return SolutionResponse.fromEntity(foundSolution);
    }

    /**
     * 카테고리별 솔루션 목록 조회
     * - 모든 사용자는 카테고리별로 구분된 솔루션 목록을 검색할 수 있다.
     * - 승인 되지 않은 솔루션은 검색되지 않는다.
     */
    public Page<SolutionListResponse> searchByCategory(String categoryName, Pageable pageable){
        log.info("카테고리별 솔루션 목록 조회");
        Page<Waste> solutionList = wasteService.findFetchByCategory(categoryName, pageable);
        return solutionList.map(SolutionListResponse::fromEntity);
    }

    /**
     * 새 솔루션 생성
     * - 로그인한 사용자는 검색이 실패한 경우 새 솔루션을 생성할 수 있다.
     * - 이름, 카테고리, 태그, 새 솔루션을 생성할 수 있다.
     */
    public Long create(Long accountId, SolutionRequest request){
        log.info("새 솔루션 생성");
        Account account = accountService.findByAccountId(accountId);
        Waste waste = wasteService.create(account, request.toEntity());
        return waste.getId();
    }

    /**
     * 사용자의 생성 기여 게시물 목록 조회
     * - 사용자는 자신을 포함한 다른 사용자의 생성 기여 게시물 목록을 조회할 수 있다.
     */
    public Page<ContributeCreationListResponse> readContributeCreations(Long accountId,
                                                                        ContributedCreationState state,
                                                                        Pageable pageable){
        log.info("사용자의 생성 기여 게시물 목록 조회");
        Page<Waste> accountCreations = wasteService.findByAccountIdAndStateWithPaging(accountId, state, pageable);
        return accountCreations.map(ContributeCreationListResponse::fromEntity);
    }

    /**
     * 솔루션 목록 조회
     * - [사용자, 관리자]는 모든 솔루션을 목록을 조회할 수 있다.
     */
    public Page<ContributeCreationListResponse> readContributeCreations(Pageable pageable){
        log.info("솔루션 목록 조회");
        Page<Waste> allCreations = wasteService.findAllFetchWithPaging(pageable);
        return allCreations.map(ContributeCreationListResponse::fromEntity);
    }

    /**
     * 솔루션 상세 조회
     * - [사용자, 관리자]는 솔루션 목록에서 솔루션을 상세 조회할 수 있다.
     */
    public SolutionResponse readContributeCreation(Long wastId){
        log.info("솔루션 상세 조회");
        Waste creation = wasteService.findFetchById(wastId);
        return SolutionResponse.fromEntity(creation);
    }

    /**
     * 새 솔루션 요청 승인
     * - 관리자는 새 솔루션 요청을 승인하여 검색 데이터로 사용할 수 있다.
     */
    public Long accept(Long wasteId){
        log.info("새 솔루션 요청 승인");
        Waste acceptCreation = wasteService.accept(wasteId);
        return acceptCreation.getId();
    }

    /**
     * 새 솔루션 요청 거부
     * - 관리자는 새 솔루션 요청을 거부할 수 있다.
     */
    public Long reject(Long wasteId){
        log.info("새 솔루션 요청 거부");
        Waste rejectCreation = wasteService.reject(wasteId);
        return rejectCreation.getId();
    }
}

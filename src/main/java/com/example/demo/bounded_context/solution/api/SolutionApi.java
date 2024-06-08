package com.example.demo.bounded_context.solution.api;

import com.example.demo.base.Resolver.AuthorizationHeader;
import com.example.demo.bounded_context.solution.dto.SolutionRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Solution", description = "솔루션 관련 API")
@RequestMapping("/api/solution")
public interface SolutionApi {

    @GetMapping("/image")
    @Operation(summary = "솔루션 이미지 검색", description = "모든 사용자는 사진을 이용한 인공지능 물질 분류에 따른 솔루션을 조회할 수 있다.")
    ResponseEntity<?> searchByImage(@RequestParam("imageUrl") String imageUrl);

    @GetMapping("/keyword")
    @Operation(summary = "솔루션 키워드 검색", description = "모든 사용자는 키워드를 사용하여 솔루션 검색을 할 수 있다.<br> 승인 되지 않은 솔루션은 검색되지 않는다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "솔루션 키워드 검색 성공"),
            @ApiResponse(responseCode = "404", description = "솔루션 키워드 검색 실패",
                    content = @Content(mediaType = "application/json", examples = {
                            @ExampleObject(value = """
                                    										{
                                    										"timestamp": "2024-06-04T21:07:08.923528",
                                                                            "cause": "EMPTY_SOLUTION",
                                                                            "message": "해당 솔루션을 찾을 수 없습니다."
                                    										}
                                    """)
                    }))
    })
    ResponseEntity<?> searchByKeyword(@RequestParam("keyword") String keyword);

    @GetMapping("/category")
    @Operation(summary = "카테고리별 솔루션 목록 조회", description = "모든 사용자는 카테고리별로 구분된 솔루션 목록을 검색할 수 있다.<br> 승인 되지 않은 솔루션은 검색되지 않는다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "카테고리별 솔루션 목록 검색 성공"),
    })
    ResponseEntity<?> searchByCategory(@RequestParam("category") String category,
                                       @PageableDefault(page = 0, size = 10) Pageable pageable);
    @PostMapping("")
    @Operation(summary = "새 솔루션 생성", description = "로그인한 사용자는 검색이 실패한 경우 새 솔루션을 생성할 수 있다.<br> 태그를 제외한 이름, 이미지, 카테고리, 솔루션 값은 필수로 입력하여 새 솔루션을 생성할 수 있다.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "새 솔루션 생성 성공"),
            @ApiResponse(responseCode = "409", description = "새 솔루션 생성 실패",
                    content = @Content(mediaType = "application/json", examples = {
                            @ExampleObject(value = """
                                    										{
                                    										"timestamp": "2024-06-04T21:07:08.923528",
                                                                            "cause": "ACCOUNT_NOT_FOUND",
                                                                            "message": "해당 계정이름을 찾을 수 없습니다."
                                    										}
                                    """)
                    }))
    })
    ResponseEntity<?> create(@AuthorizationHeader Long accountId,
                             @RequestBody SolutionRequest request);

    @GetMapping("")
    @Operation(summary = "솔루션 목록 조회", description = "사용자는 모든 솔루션을 목록을 조회할 수 있다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "모든 새 솔루션 목록 페이지 조회 성공")
    })
    ResponseEntity<?> readContributeCreationList(@PageableDefault(page = 0, size = 10) Pageable pageable);

    @GetMapping("/{wasteId}")
    @Operation(summary = "솔루션 상세 조회", description = "사용자는 솔루션 목록에서 솔루션을 상세 조회할 수 있다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "솔루션 상세 조회 성공")
    })
    ResponseEntity<?> readContributeCreation(@PathVariable Long wasteId);

    @PutMapping("/{wasteId}/accepted")
    @Operation(summary = "새 솔루션 요청 승인", description = "관리자는 새 솔루션 요청을 승인하여 검색 데이터로 사용할 수 있다.")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "새 솔루션 요청 승인 성공"),
            @ApiResponse(responseCode = "404", description = "새 솔루션 요청 승인 실패",
                    content = @Content(mediaType = "application/json", examples = {
                            @ExampleObject(value = """
                                    										{
                                    										"timestamp": "2024-06-04T21:07:08.923528",
                                                                            "cause": "SOLUTION_NOT_FOUND",
                                                                            "message": "해당 솔루션을 찾을 수 없습니다."
                                    										}
                                    """)
                    }))
    })
    ResponseEntity<?> accept(@PathVariable Long wasteId);

    @PutMapping("/{wasteId}/rejected")
    @Operation(summary = "새 솔루션 요청 거부", description = "관리자는 새 솔루션 요청을 거부할 수 있다.")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "새 솔루션 요청 거부 성공"),
            @ApiResponse(responseCode = "404", description = "새 솔루션 요청 거부 실패",
                    content = @Content(mediaType = "application/json", examples = {
                            @ExampleObject(value = """
                                    										{
                                    										"timestamp": "2024-06-04T21:07:08.923528",
                                                                            "cause": "SOLUTION_NOT_FOUND",
                                                                            "message": "해당 솔루션이 존재하지 않습니다."
                                    										}
                                    """)
                    }))
    })
    ResponseEntity<?> reject(@PathVariable Long wasteId);
}

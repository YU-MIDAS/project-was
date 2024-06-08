package com.example.demo.bounded_context.wiki.service;

import com.example.demo.base.exception.CustomException;
import com.example.demo.base.exception.ExceptionCode;
import com.example.demo.bounded_context.account.entity.Account;
import com.example.demo.bounded_context.wiki.dto.WikiCompareResponse;
import com.example.demo.bounded_context.wiki.dto.WikiRequest;
import com.example.demo.bounded_context.wiki.dto.WikiResponse;
import com.example.demo.bounded_context.solution.entity.*;
import com.example.demo.bounded_context.wiki.repository.WikiRepository;
import com.example.demo.bounded_context.solution.service.WasteService;
import com.example.demo.bounded_context.wiki.entity.Wiki;
import com.example.demo.bounded_context.wiki.entity.WikiState;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class WikiService {
    private final WikiRepository wikiRepository;
    private final WasteService wasteService;

    @Transactional(readOnly = true)
    public Wiki findByWikiId(Long wikiId){
        log.trace("select proxy wiki by id");
        return wikiRepository.findById(wikiId)
                .orElseThrow(() -> new CustomException(ExceptionCode.EMPTY_WIKI));
    }

    @Transactional(readOnly = true)
    public Wiki findFetchByWikiId(Long wikiId){
        log.trace("select fetch wiki by id");
        return wikiRepository.findFetchById(wikiId)
                .orElseThrow(() -> new CustomException(ExceptionCode.EMPTY_WIKI));
    }

    @Transactional(readOnly = true)
    public Page<Wiki> findByAccountIdAndState(Long accountId, WikiState state, Pageable pageable){
        return wikiRepository.findByAccountIdAndStateWithPaging(accountId, state, pageable);
    }

    @Transactional(readOnly = true)
    public void isExistNonAccept(Account writer, Waste waste){
        log.info("보류중인 위키 존재 검사");
        if(wikiRepository.existsPending(writer.getId(), waste.getId()).isPresent()){
            throw new CustomException(ExceptionCode.EXIST_WIKI);
        };
    }

    @Transactional(readOnly = true)
    public Wiki verifyWikiAuthor(Long userId, Long wikiId){
        log.info("위키 작성자 검사");
        Wiki wiki = findByWikiId(wikiId);
        if(!wiki.getWriter().getId().equals(userId))
            throw new CustomException(ExceptionCode.INVALID_WIKI_WRITER);
        return wiki;
    }

    @Transactional(readOnly = true)
    public WikiCompareResponse read(Long wikiId){
        log.info("위키 정보 조회");
        Wiki foundWiki = findFetchByWikiId(wikiId);
        WikiResponse origin = foundWiki.getOriginal() == null ? null : WikiResponse.fromEntity(foundWiki.getOriginal());
        WikiResponse modified = WikiResponse.fromEntity(foundWiki);
        return WikiCompareResponse.of(origin, modified);
    }

    @Transactional
    public Wiki create(Account writer, Waste waste, WikiRequest request){
        log.info("위키 요청 생성");
        isExistNonAccept(writer, waste);
        Optional<Wiki> parentWiki = wikiRepository.findByRecent(waste.getId());
        Wiki wiki = request.toEntity(writer, waste, parentWiki.orElse(null));
        return wikiRepository.save(wiki);
    }

    @Transactional
    public void updateWriter(Account writer){
        wikiRepository.updateWriter(writer);
    }

    @Transactional
    public void delete(Long userId, Long wikiId){
        log.info("위키 요청 삭제");
        Wiki foundWiki = verifyWikiAuthor(userId, wikiId);
        if(foundWiki.getWikiState() != WikiState.PENDING){
            throw new CustomException(ExceptionCode.NOT_PENDING_WIKI);
        }
        wikiRepository.delete(foundWiki);
    }

    /**
     * [] delete insert update 여러번 문제 수정 필요
     */
    @Transactional
    public void accept(Long wikiId){
        log.info("위키 요청 반영");
        Wiki wiki = findFetchByWikiId(wikiId);
        Waste waste = wasteService.findFetchById(wiki.getWaste().getId());

        waste.getTags().clear();
        waste.getCategories().clear();

        String[] categoriesSplit = wiki.getCategories().split(",");
        for(String categoryName : categoriesSplit){
            Category category = Category.builder()
                    .name(categoryName)
                    .waste(waste)
                    .build();
            waste.getCategories().add(category);
        }

        String[] tagsSplit = wiki.getTags().split(",");
        for(String tagName : tagsSplit){
            Tag tag = Tag.builder()
                    .name(tagName)
                    .waste(waste)
                    .build();
            waste.getTags().add(tag);
        }

        wiki.accept();
        waste.getWikis().stream()
                .filter(w -> w.getWikiState().equals(WikiState.PENDING))
                .forEach(w -> w.changeOriginal(wiki));
        waste.update(wiki.getSolution());
    }

    @Transactional
    public void reject(Long wikiId){
        log.info("위키 요청 거절");
        Wiki foundWiki = findByWikiId(wikiId);
        foundWiki.reject();
    }
}

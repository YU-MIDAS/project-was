package com.example.demo.bounded_context.wiki.service;

import com.example.demo.bounded_context.solution.entity.ContributedCreationState;
import com.example.demo.bounded_context.wiki.dto.ContributeModificationsResponse;
import com.example.demo.bounded_context.wiki.dto.WikiListResponse;
import com.example.demo.bounded_context.wiki.entity.Wiki;
import com.example.demo.bounded_context.wiki.entity.WikiState;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class WikiUseCase {
    private final WikiService wikiService;

    public List<ContributeModificationsResponse> readContributeModifications(Long accountId,
                                                                          WikiState state,
                                                                          Pageable pageable){
        List<Wiki> modifications = wikiService.findByAccountIdAndState(accountId, state, pageable);
        return modifications.stream().map(ContributeModificationsResponse::fromEntity).toList();
    }
}
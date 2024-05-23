package com.example.demo.bounded_context.solution.dto;

import com.example.demo.bounded_context.account.entity.Account;
import com.example.demo.bounded_context.solution.entity.Opinion;
import com.example.demo.bounded_context.solution.entity.Waste;
import lombok.Builder;
import lombok.Getter;

@Getter
public class RequestOpinion {
    private String name;
    private String solution;
    private String materials;
    private String tags;
    private Boolean isAccept;

    public Opinion toEntity(Account writer, Waste waste){
        return Opinion.builder()
                .writer(writer)
                .waste(waste)
                .name(name)
                .materials(materials)
                .tags(tags)
                .solution(solution)
                .isAccept(false)
                .build();
    }
}

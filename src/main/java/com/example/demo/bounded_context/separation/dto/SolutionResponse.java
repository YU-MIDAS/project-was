package com.example.demo.bounded_context.separation.dto;

import com.example.demo.bounded_context.category.entity.MainCategory;
import lombok.Builder;
import lombok.Getter;


@Getter
public class SolutionResponse {
    String name;
    String solution;

    @Builder
    public SolutionResponse(String name, String solution) {
        this.name = name;
        this.solution = solution;
    }

    public static SolutionResponse of(MainCategory mainCategory){
        return SolutionResponse.builder()
                .name(mainCategory.getCategoryName())
                .solution(mainCategory.getSolution())
                .build();
    }
}

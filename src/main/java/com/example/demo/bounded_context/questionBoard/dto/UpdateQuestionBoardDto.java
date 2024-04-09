package com.example.demo.bounded_context.questionBoard.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UpdateQuestionBoardDto {
    private String title;

    private String content;

    private String imageUrl;
}

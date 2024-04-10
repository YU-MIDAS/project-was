package com.example.demo.bounded_context.questionBoard.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpdateQuestionBoardDto {
    private String title;

    private String content;

    private String imageUrl;
}

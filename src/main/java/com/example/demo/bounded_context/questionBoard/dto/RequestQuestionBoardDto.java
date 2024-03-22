package com.example.demo.bounded_context.questionBoard.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RequestQuestionBoardDto {
    private String trashName;

    private String content;
}

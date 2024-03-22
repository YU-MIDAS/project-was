package com.example.demo.bounded_context.questionBoard.dto;

import com.example.demo.bounded_context.account.entity.Account;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ResponseQuestionBoardDto {
    private String trashName;

    private String content;

    private Integer score;

    private Account writer;
}

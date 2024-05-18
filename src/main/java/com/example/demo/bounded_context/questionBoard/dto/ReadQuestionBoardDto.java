package com.example.demo.bounded_context.questionBoard.dto;

import com.example.demo.bounded_context.account.entity.Account;
import com.example.demo.bounded_context.questionBoard.entity.QuestionBoard;
import com.example.demo.bounded_context.questionComment.dto.QuestionCommentResponseDto;
import com.example.demo.bounded_context.questionComment.entity.QuestionComment;
import lombok.*;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReadQuestionBoardDto {
    private String title;

    private String content;

    private Integer recommend;

    private Account writer;

    private String imageUrl;

    private boolean adopted;

    private List<QuestionCommentResponseDto> comments;

    private QuestionComment adoptedComment;
    public ReadQuestionBoardDto(QuestionBoard questionBoard) {
        this.title = questionBoard.getTitle();
        this.content = questionBoard.getContent();
        this.recommend = questionBoard.getRecommend();
        this.writer = questionBoard.getWriter();
        this.imageUrl=questionBoard.getImageUrl();
        this.adopted=questionBoard.isAdopted();
        this.comments=questionBoard.getComments().stream().map(QuestionCommentResponseDto::new).collect(Collectors.toList());
        this.adoptedComment=questionBoard.getAdoptedComment();
    }


}

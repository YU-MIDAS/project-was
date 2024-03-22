package com.example.demo.bounded_context.questionBoard.entity;


import com.example.demo.base.common.BaseTimeEntity;
import com.example.demo.bounded_context.account.entity.Account;
import com.example.demo.bounded_context.questionBoard.dto.RequestQuestionBoardDto;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class QuestionBoard extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String trashName; //타이틀 대용?

    private String content;

    private Integer score;

    @JoinColumn(name = "WRITER_ID", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private Account writer;

    @Builder
    public QuestionBoard(String trashName, String content, Integer score, Account writer){
        this.trashName = trashName;
        this.content = content;
        this.score = score;
        this.writer = writer;
    }

    public void update(RequestQuestionBoardDto requestQuestionBoardDto) {
        this.trashName = requestQuestionBoardDto.getTrashName();
        this.content = requestQuestionBoardDto.getContent();
    }
}

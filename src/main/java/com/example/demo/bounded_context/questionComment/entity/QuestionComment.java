package com.example.demo.bounded_context.questionComment.entity;

import com.example.demo.base.common.BaseTimeEntity;
import com.example.demo.bounded_context.account.entity.Account;
import com.example.demo.bounded_context.questionBoard.entity.QuestionBoard;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class QuestionComment extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String content;

    private Integer recommend;

    @JoinColumn(name = "QUESTION_ID", nullable = false)
    @ManyToOne
    private QuestionBoard questionBoard;

    @JoinColumn(name = "WRITER_ID", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private Account writer;

}

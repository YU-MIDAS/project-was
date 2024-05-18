package com.example.demo.bounded_context.questionBoard.entity;


import com.example.demo.base.common.BaseTimeEntity;
import com.example.demo.bounded_context.account.entity.Account;
import com.example.demo.bounded_context.questionBoard.dto.UpdateQuestionBoardDto;
import com.example.demo.bounded_context.questionComment.entity.QuestionComment;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class QuestionBoard extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    private String content;

    private String imageUrl; //참조X 자체컬럼

    private boolean adopted; //채택유무

    @JoinColumn(name = "ADOPTED_WRITER_ID", nullable = true)
    @OneToOne(fetch = FetchType.EAGER)
    private QuestionComment adoptedComment; //채택된 댓글

    @OneToMany(mappedBy = "questionBoard", fetch = FetchType.EAGER, cascade = CascadeType.REMOVE) //게시글 삭제시 삭제
    private List<QuestionComment> comments;

    @JoinColumn(name = "QUESTION_WRITER_ID", nullable = false)
    @ManyToOne(fetch = FetchType.EAGER)
    private Account writer;

    @Column(columnDefinition = "integer default 0", nullable = false)
    private Integer recommend; //추천수, 추천기능을 위해선 엔티티를 하나 더 작성해야한다고 생각,,(멤버,보드 관계엔티티로써)

    @Builder
    public QuestionBoard(String title, String content, Integer recommend, Account writer, String imageUrl, Boolean adopted){
        this.title = title;
        this.content = content;
        this.recommend = recommend;
        this.writer = writer;
        this.imageUrl = imageUrl;
        this.adopted = adopted;
    }

    public void update(UpdateQuestionBoardDto updateQuestionBoardDto) {
        this.title = updateQuestionBoardDto.getTitle();
        this.content = updateQuestionBoardDto.getContent();
        this.imageUrl = updateQuestionBoardDto.getImageUrl();
    }


    public void adopting(QuestionComment comment) {
        this.adoptedComment = comment;
        this.adopted = true;
    }
}

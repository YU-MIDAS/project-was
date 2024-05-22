package com.example.demo.bounded_context.questionComment.service;

import com.example.demo.bounded_context.account.entity.Account;
import com.example.demo.bounded_context.questionBoard.entity.QuestionBoard;
import com.example.demo.bounded_context.questionBoard.repository.QuestionBoardRepository;
import com.example.demo.bounded_context.questionComment.entity.QuestionComment;
import com.example.demo.bounded_context.questionComment.repository.QuestionCommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class QuestionCommentService {
    private final QuestionCommentRepository questionCommentRepository;
    private final QuestionBoardRepository questionBoardRepository;

    //c
    @Transactional
    public void create(String content, Account writer, Long id){
        QuestionBoard questionBoard = questionBoardRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("QUESTIONBOARD_NOT_FOUND"));

        QuestionComment questionComment = QuestionComment.builder()
                .content(content)
                .recommend(0)
                .questionBoard(questionBoard)
                .writer(writer)
                .build();

        questionCommentRepository.save(questionComment);
    }

    //r
    @Transactional
    public QuestionComment read(Long questionCommentId){
        return questionCommentRepository.findById(questionCommentId)
                .orElseThrow(() -> new IllegalArgumentException("QUESTIONCOMMENT_NOT_FOUND"));
    }

    //u
    @Transactional
    public void update(Long questionCommentId, String content){
        QuestionComment questionComment = questionCommentRepository.findById(questionCommentId)
                .orElseThrow(() -> new IllegalArgumentException("QUESTIONCOMMENT_NOT_FOUND"));

        questionComment.update(content);
    }

    //d
    @Transactional
    public void delete(Long questionCommentId){
        QuestionComment questionComment = questionCommentRepository.findById(questionCommentId)
                .orElseThrow(() -> new IllegalArgumentException("QUESTIONCOMMENT_NOT_FOUND"));

        questionCommentRepository.delete(questionComment);
    }


}

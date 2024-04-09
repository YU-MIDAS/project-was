package com.example.demo.bounded_context.questionBoard.service;


import com.example.demo.bounded_context.account.entity.Account;
import com.example.demo.bounded_context.questionBoard.dto.CreateQuestionBoardDto;
import com.example.demo.bounded_context.questionBoard.dto.ReadQuestionBoardDto;
import com.example.demo.bounded_context.questionBoard.dto.UpdateQuestionBoardDto;
import com.example.demo.bounded_context.questionBoard.entity.QuestionBoard;
import com.example.demo.bounded_context.questionBoard.repository.QuestionBoardRepository;
import com.example.demo.bounded_context.questionComment.entity.QuestionComment;
import com.example.demo.bounded_context.questionComment.repository.QuestionCommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class QuestionBoardService {
    private final QuestionBoardRepository questionBoardRepository;
    private final QuestionCommentRepository questionCommentRepository;

    //c
    @Transactional
    public void create(CreateQuestionBoardDto createQuestionBoardDto, Account writer){
        QuestionBoard questionBoard = QuestionBoard.builder()
                .title(createQuestionBoardDto.getTitle())
                .content(createQuestionBoardDto.getContent())
                .imageUrl(createQuestionBoardDto.getImageUrl())
                .category(createQuestionBoardDto.getCategory())
                .recommend(0)
                .adopted(false)
                .writer(writer)
                .build();

        questionBoardRepository.save(questionBoard);
    }

    //r
    @Transactional
    public ReadQuestionBoardDto read(Long questionBoardId){
        QuestionBoard questionBoard = questionBoardRepository.findById(questionBoardId)
                .orElseThrow(() -> new IllegalArgumentException("QUESTIONBOARD_NOT_FOUND"));

        return new ReadQuestionBoardDto(questionBoard);
    }

    //u
    @Transactional
    public void update(Long questionBoardId, UpdateQuestionBoardDto updateQuestionBoardDto){
        QuestionBoard questionBoard = questionBoardRepository.findById(questionBoardId)
                .orElseThrow(() -> new IllegalArgumentException("QUESTIONBOARD_NOT_FOUND"));

        questionBoard.update(updateQuestionBoardDto);
    }

    //d
    @Transactional
    public void delete(Long questionBoardId){
        QuestionBoard questionBoard = questionBoardRepository.findById(questionBoardId)
                .orElseThrow(() -> new IllegalArgumentException("QUESTIONBOARD_NOT_FOUND"));

        questionBoardRepository.delete(questionBoard);
    }

    @Transactional //댓글 채택
    public void adopting(Long questionBoardId, Long commentId){
        QuestionBoard questionBoard = questionBoardRepository.findById(questionBoardId)
                .orElseThrow(() -> new IllegalArgumentException("QUESTIONBOARD_NOT_FOUND"));

        QuestionComment comment = questionCommentRepository.findById(commentId)
                .orElseThrow(() -> new IllegalArgumentException("COMMENT_NOT_FOUND"));

        questionBoard.adopting(comment);
    }
}

package com.example.demo.bounded_context.questionBoard.service;


import com.example.demo.bounded_context.account.entity.Account;
import com.example.demo.bounded_context.questionBoard.dto.RequestQuestionBoardDto;
import com.example.demo.bounded_context.questionBoard.dto.ResponseQuestionBoardDto;
import com.example.demo.bounded_context.questionBoard.entity.QuestionBoard;
import com.example.demo.bounded_context.questionBoard.repository.QuestionBoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class QuestionBoardService {
    private final QuestionBoardRepository questionBoardRepository;

    //c
    @Transactional
    public void createQuestionBoard(RequestQuestionBoardDto requestQuestionBoardDto, Account writer){
        QuestionBoard questionBoard = QuestionBoard.builder()
                .trashName(requestQuestionBoardDto.getTrashName())
                .content(requestQuestionBoardDto.getContent())
                .score(0)
                .writer(writer)
                .build();

        questionBoardRepository.save(questionBoard);
    }

    //r
    @Transactional
    public ResponseQuestionBoardDto getQuestionBoard(Long questionBoardId){
        QuestionBoard questionBoard = questionBoardRepository.findById(questionBoardId)
                .orElseThrow(() -> new IllegalArgumentException("QUESTIONBOARD_NOT_FOUND"));

        return ResponseQuestionBoardDto.builder()
                .trashName(questionBoard.getTrashName())
                .content(questionBoard.getContent())
                .score(questionBoard.getScore())
                .writer(questionBoard.getWriter())
                .build();
    }

    //u
    @Transactional
    public void updateQuestionBoard(Long questionBoardId, RequestQuestionBoardDto requestQuestionBoardDto){
        QuestionBoard questionBoard = questionBoardRepository.findById(questionBoardId)
                .orElseThrow(() -> new IllegalArgumentException("QUESTIONBOARD_NOT_FOUND"));

        questionBoard.update(requestQuestionBoardDto);
    }

    //d
    @Transactional
    public void deleteQuestionBoard(Long questionBoardId){
        QuestionBoard questionBoard = questionBoardRepository.findById(questionBoardId)
                .orElseThrow(() -> new IllegalArgumentException("QUESTIONBOARD_NOT_FOUND"));

        questionBoardRepository.delete(questionBoard);
    }
}

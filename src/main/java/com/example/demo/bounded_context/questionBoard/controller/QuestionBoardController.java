package com.example.demo.bounded_context.questionBoard.controller;

import com.example.demo.base.Resolver.AuthorizationHeader;
import com.example.demo.bounded_context.account.entity.Account;
import com.example.demo.bounded_context.account.service.AccountService;
import com.example.demo.bounded_context.questionBoard.dto.CreateQuestionBoardDto;
import com.example.demo.bounded_context.questionBoard.dto.ReadQuestionBoardDto;
import com.example.demo.bounded_context.questionBoard.dto.UpdateQuestionBoardDto;
import com.example.demo.bounded_context.questionBoard.service.QuestionBoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/questionBoard")
public class QuestionBoardController {
    private final QuestionBoardService questionBoardService;
    private final AccountService accountService;

    @PostMapping // - 로그인한 사용자는 게시글을 작성할 수 있다
    public ResponseEntity<?> createQuestionBoard(@AuthorizationHeader Long id, @RequestBody CreateQuestionBoardDto createQuestionBoardDto){
        Account writer = accountService.read(id);; // writer가 현재 누구
        questionBoardService.create(createQuestionBoardDto, writer);

        return ResponseEntity.ok("게시글 작성 성공");
    }

    // 게시글 목록(리스트) 보기(페이징 필요?)

    @GetMapping("/read/{questionBoardId}") // - 게시글 자세히 보기, (댓글-페이지네이션 필요?)
    public ResponseEntity<ReadQuestionBoardDto> readQuestionBoardById(@PathVariable Long questionBoardId) {
        ReadQuestionBoardDto readQuestionBoardDto = questionBoardService.read(questionBoardId);

        return ResponseEntity.ok(readQuestionBoardDto);
    }



    @PostMapping("/update/{questionBoardId}") // - 게시글 업데이트
    public ResponseEntity<?> updateQuestionBoard(@AuthorizationHeader Long id,
                                                 @PathVariable Long questionBoardId,
                                                 @RequestBody UpdateQuestionBoardDto updateQuestionBoardDto) {
        Account user = accountService.read(id);
        ReadQuestionBoardDto readQuestionBoardDto = questionBoardService.read(questionBoardId);
        if(readQuestionBoardDto.getWriter()==user){
            questionBoardService.update(questionBoardId,updateQuestionBoardDto);
            return ResponseEntity.ok("게시글 수정완료");
        }
        else{
            return ResponseEntity.ok("권한이 없습니다");
        }
    }

    @GetMapping("/delete/{questionBoardId}") // - 게시글 삭제
    public ResponseEntity<?> deleteQuestionBoard(@AuthorizationHeader Long id, @PathVariable Long questionBoardId) {
        Account user = accountService.read(id);
        ReadQuestionBoardDto readQuestionBoardDto = questionBoardService.read(questionBoardId);
        if(readQuestionBoardDto.getWriter()==user){
            questionBoardService.delete(questionBoardId);
            return ResponseEntity.ok("게시글 삭제완료");
        }
        else{
            return ResponseEntity.ok("권한이 없습니다");
        }
    }

    @PostMapping("/read/{questionBoardId}/{questionCommentId}") // - 댓글 채택
    public ResponseEntity<?> adoptingQuestionBoardComment(@AuthorizationHeader Long id,
                                                          @PathVariable Long questionBoardId,
                                                          @PathVariable Long questionCommentId){
        Account user = accountService.read(id);
        ReadQuestionBoardDto readQuestionBoardDto = questionBoardService.read(questionBoardId);
        if(readQuestionBoardDto.getWriter()==user&& !readQuestionBoardDto.isAdopted()){
            questionBoardService.adopting(questionBoardId,questionCommentId);
            return ResponseEntity.ok("댓글 채택 완료");
        }
        else{
            return ResponseEntity.ok("권한이 없습니다");
        }
    }




}

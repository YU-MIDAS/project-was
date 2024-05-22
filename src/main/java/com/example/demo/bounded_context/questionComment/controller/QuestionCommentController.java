package com.example.demo.bounded_context.questionComment.controller;

import com.example.demo.base.Resolver.AuthorizationHeader;
import com.example.demo.bounded_context.account.entity.Account;
import com.example.demo.bounded_context.account.service.AccountService;
import com.example.demo.bounded_context.questionComment.entity.QuestionComment;
import com.example.demo.bounded_context.questionComment.service.QuestionCommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/questionComment")
public class QuestionCommentController {
    private final QuestionCommentService questionCommentService;
    private final AccountService accountService;

    @PostMapping("/{questionBoardId}/create") // - 로그인한 사용자는 댓글을 작성할 수 있다
    public ResponseEntity<?> createQuestionBoard(@PathVariable Long questionBoardId,
                                                 @AuthorizationHeader Long id,
                                                 @RequestBody String content){
        Account writer = accountService.read(id);; // writer가 현재 누구
        questionCommentService.create(content,writer,questionBoardId);

        return ResponseEntity.ok("댓글 생성완료");
    }

    @PutMapping("/update/{questionCommentId}") // - 댓글 업데이트
    public ResponseEntity<?> updateQuestionComment(@AuthorizationHeader Long id,
                                                   @PathVariable Long questionCommentId,
                                                   @RequestBody String content) {
        Account user = accountService.read(id);
        QuestionComment questionComment=questionCommentService.read(questionCommentId);
        if(questionComment.getWriter()==user){
            questionCommentService.update(questionCommentId,content);
            return ResponseEntity.ok("댓글 수정완료");
        }
        else{
            return ResponseEntity.ok("권한이 없습니다");
        }
    }

    @GetMapping("/delete/{questionCommentId}")  // - 댓글 삭제
    public ResponseEntity<?> deleteQuestionComment(@AuthorizationHeader Long id,
                                                   @PathVariable Long questionCommentId) {
        Account user = accountService.read(id);
        QuestionComment questionComment=questionCommentService.read(questionCommentId);
        if(questionComment.getWriter()==user){
            questionCommentService.delete(questionCommentId);
            return ResponseEntity.ok("댓글 삭제완료");
        }
        else{
            return ResponseEntity.ok("권한이 없습니다");
        }
    }

}

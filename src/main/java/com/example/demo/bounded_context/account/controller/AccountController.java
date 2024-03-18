package com.example.demo.bounded_context.account.controller;

import com.example.demo.base.Resolver.AccessToken;
import com.example.demo.base.Resolver.AuthorizationHeader;
import com.example.demo.base.common.AuthConstants;
import com.example.demo.bounded_context.account.entity.Account;
import com.example.demo.bounded_context.account.service.AccountService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/account")
public class AccountController {
    private final AccountService accountService;

    @GetMapping("/list")
    public List<Account> list(){
        return accountService.list();
    }

    @GetMapping("/info")
    public Account info(@AuthorizationHeader Long id){
        return accountService.read(id);
    }

    @PostMapping("/sign-out")
    @Operation(summary = "로그아웃", description = "access / refresh token 을 사용한 로그아웃")
    public ResponseEntity signOut(@AuthorizationHeader AccessToken accessToken,
                                  @CookieValue(AuthConstants.REFRESH_TOKEN) String refreshToken) {
        accountService.signOut(accessToken, refreshToken);
        return ResponseEntity.ok().body("로그아웃에 성공했습니다.");
    }
}

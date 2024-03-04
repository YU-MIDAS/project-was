package com.example.demo.bounded_context.auth.controller;

import com.example.demo.base.jwt.JwtProvider;
import com.example.demo.bounded_context.auth.dto.SignInAccountRequest;
import com.example.demo.bounded_context.auth.dto.SignUpAccountRequest;
import com.example.demo.bounded_context.auth.service.AuthService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;
    private final JwtProvider jwtProvider;

    @PostMapping("/sign-up")
    public ResponseEntity<String> signUp(@RequestBody SignUpAccountRequest request) {
        authService.signUp(request);
        return ResponseEntity.ok().body("회원가입에 성공했습니다.");
    }

    /**
     * 아이디 / 비밀번호로 로그인
     */
    @PostMapping("/sign-in")
    public ResponseEntity signIn(@RequestBody SignInAccountRequest request) {
        return ResponseEntity.ok().body(authService.signIn(request));
    }

    /**
     * refresh token 이 정상적인 경우 access token / refresh token 재발급
     */
    @PostMapping("/token")
    public ResponseEntity token(HttpServletRequest request) {
        String refreshToken = jwtProvider.parseToken(request);
        return ResponseEntity.ok().body(authService.reIssueToken(refreshToken));
    }
}
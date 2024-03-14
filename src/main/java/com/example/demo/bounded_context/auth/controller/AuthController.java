package com.example.demo.bounded_context.auth.controller;

import com.example.demo.base.jwt.JwtProvider;
import com.example.demo.bounded_context.auth.dto.SignInAccountRequest;
import com.example.demo.bounded_context.auth.dto.SignUpAccountRequest;
import com.example.demo.bounded_context.auth.dto.TokenResponse;
import com.example.demo.bounded_context.auth.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Auth", description = "사용자 인증 관련 API")
public class AuthController {

    private final AuthService authService;
    private final JwtProvider jwtProvider;

    @PostMapping("/sign-up")
    @Operation(summary = "기본 회원가입", description = "아이디 / 비밀번호를 사용한 회원가입")
    public ResponseEntity signUp(@RequestBody SignUpAccountRequest request) {
        authService.signUp(request);
        return ResponseEntity.ok().body("회원가입에 성공했습니다.");
    }

    @PostMapping("/sign-in")
    @Operation(summary = "기본 로그인", description = "아이디 / 비밀번호를 사용한 로그인")
    public ResponseEntity<TokenResponse> signIn(@RequestBody SignInAccountRequest request) {
        return ResponseEntity.ok().body(authService.signIn(request));
    }

    @PostMapping("/token")
    @Operation(summary = "토큰 재발급", description = "refresh token 이 정상적인 경우 access/refresh token 재발급 (RTR)")
    public ResponseEntity<TokenResponse> token(HttpServletRequest request) {
        String refreshToken = jwtProvider.parseToken(request);
        return ResponseEntity.ok().body(authService.reIssueToken(refreshToken));
    }
}
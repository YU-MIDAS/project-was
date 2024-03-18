package com.example.demo.base.common;

import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Component;

import java.time.Duration;

@Component
public class CookieProvider {
    public ResponseCookie generateTokenCookie(String refreshToken){
        return ResponseCookie.from("RefreshToken", refreshToken)
                .httpOnly(true)
                .sameSite("None")
                .path("/api/auth")
                .maxAge(Duration.ofDays(7))
                .build();
    }
}


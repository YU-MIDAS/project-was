package com.example.demo.bounded_context.auth.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class TokenResponse {
    String accessToken;
    String refreshToken;

    @Builder
    public TokenResponse(String accessToken, String refreshToken) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }
}

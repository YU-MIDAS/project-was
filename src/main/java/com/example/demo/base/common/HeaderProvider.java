package com.example.demo.base.common;

import com.example.demo.bounded_context.auth.dto.TokenResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class HeaderProvider {
    private final CookieProvider cookieProvider;

    public HttpHeaders generateTokenHeader(TokenResponse tokenResponse){
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set(HttpHeaders.AUTHORIZATION, tokenResponse.accessToken());
        httpHeaders.set(HttpHeaders.SET_COOKIE,
                cookieProvider.generateTokenCookie(tokenResponse.refreshToken()).toString());
        return httpHeaders;
    }
}

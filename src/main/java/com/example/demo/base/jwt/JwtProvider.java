package com.example.demo.base.jwt;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class JwtProvider {

    private final JwtProperties jwtProperties;
    private final String AUTH_HEADER = "Authorization";
    private final String BEARER = "Bearer ";

    public String generatorAccessToken(Long userId){
        Date now = new Date();
        return Jwts.builder()
                .setIssuer(jwtProperties.getIssuer())
                .claim("userId", userId)
                .signWith(SignatureAlgorithm.HS256, jwtProperties.getSecretKey().getBytes())
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + jwtProperties.getAccessExpiration()))
                .compact();
    }

    public String parseToken(HttpServletRequest request) throws Exception{
        return Optional.ofNullable(request.getHeader(AUTH_HEADER))
                .filter(token -> token.startsWith(BEARER))
                .map(token -> token.replace(BEARER, ""))
                .orElseThrow(() -> new Exception ("토큰이 존재하지 않습니다. "));
    }
}

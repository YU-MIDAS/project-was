package com.example.demo.base.jwt;

import com.example.demo.base.common.AuthConstants;
import com.example.demo.base.exception.CustomException;
import com.example.demo.base.exception.ExceptionCode;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class JwtProvider {

    private final JwtProperties jwtProperties;

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

    public String parseToken(HttpServletRequest request) throws CustomException {
        return Optional.ofNullable(request.getHeader(AuthConstants.AUTH_HEADER))
                .filter(token -> token.startsWith(AuthConstants.BEARER))
                .map(token -> token.replace(AuthConstants.BEARER, ""))
                .orElseThrow(() -> new CustomException(ExceptionCode.EMPTY_TOKEN));
    }

    public Claims getValidToken(String token){
        return Jwts.parserBuilder()
                .setSigningKey(jwtProperties.getSecretKey().getBytes())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}

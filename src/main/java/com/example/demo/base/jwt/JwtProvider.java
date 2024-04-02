package com.example.demo.base.jwt;

import com.example.demo.base.common.AuthConstants;
import com.example.demo.base.exception.CustomException;
import com.example.demo.base.exception.ExceptionCode;
import io.jsonwebtoken.*;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import java.util.Date;

@Component
@RequiredArgsConstructor
public class JwtProvider {

    private final JwtProperties jwtProperties;

    public String generatorAccessToken(Long accountId){
        Date now = new Date();
        return Jwts.builder()
                .setIssuer(jwtProperties.getIssuer())
                .claim("accountId", accountId)
                .signWith(SignatureAlgorithm.HS256, jwtProperties.getSecretKey().getBytes())
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + jwtProperties.getAccessExpiration()))
                .compact();
    }

    public String parseToken(HttpServletRequest request) {
        if(request.getHeader(AuthConstants.AUTH_HEADER) == null){
            throw new CustomException(ExceptionCode.INVALID_HEADER);
        }
        return parseToken(request.getHeader(AuthConstants.AUTH_HEADER));
    }

    public String parseToken(String token){
        if(token.startsWith(AuthConstants.BEARER)){
            return token.substring(AuthConstants.BEARER.length());
        }else{
            throw new CustomException(ExceptionCode.WRONG_TOKEN);
        }
    }

    public Claims getValidToken(String token){
        return Jwts.parserBuilder()
                .setSigningKey(jwtProperties.getSecretKey().getBytes())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public String getAccountId(String token){
        return getValidToken(token).get("accountId").toString();
    }
}

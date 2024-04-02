package com.example.demo.base.exception;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.security.SignatureException;

import java.util.Map;

public class JwtExceptionProvider {
    private static final Map<Class<? extends Exception>, ExceptionCode> exceptionMappings =
            Map.ofEntries(
                    Map.entry(SignatureException.class, ExceptionCode.INVALID_TOKEN),
                    Map.entry(MalformedJwtException.class, ExceptionCode.WRONG_TOKEN),
                    Map.entry(ExpiredJwtException.class, ExceptionCode.EXPIRE_ACCESS_TOKEN)
            );

    public static ExceptionResponse generatorExceptionResponse(Exception exception) {
        if (exception instanceof JwtException){ //인증 관련
            return ExceptionResponse.of(exceptionMappings.get(exception.getClass()));
        } else if(exception instanceof CustomException){ //파싱 관련
            return ExceptionResponse.of(((CustomException) exception).getExceptionCode());
        }
        return ExceptionResponse.of(exception);
    }
}

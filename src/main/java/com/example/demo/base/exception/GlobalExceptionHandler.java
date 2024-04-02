package com.example.demo.base.exception;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.security.SignatureException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MissingRequestCookieException;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ExceptionResponse> handler(CustomException e){
        ExceptionCode exceptionCode = e.getExceptionCode();
        ExceptionResponse response = ExceptionResponse.of(exceptionCode);
        return ResponseEntity.status(exceptionCode.getStatus()).body(response);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ExceptionResponse> handler(BadCredentialsException e){
        ExceptionResponse response = ExceptionResponse.of(ExceptionCode.INVALID_PASSWORD);
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(response);
    }

    /**
     * API 요청 에러
     */
    //BODY
    @ExceptionHandler(HttpMessageNotReadableException.class)
    protected ResponseEntity<ExceptionResponse> handler(HttpMessageNotReadableException e) {
        ExceptionResponse response = ExceptionResponse.of(ExceptionCode.INVALID_BODY);
        return ResponseEntity.badRequest().body(response);
    }

    //HEADER
    @ExceptionHandler(MissingRequestHeaderException.class)
    protected ResponseEntity<ExceptionResponse> handler(MissingRequestHeaderException e) {
        ExceptionResponse response = ExceptionResponse.of(ExceptionCode.INVALID_HEADER);
        return ResponseEntity.badRequest().body(response);
    }

    //COOKIE
    @ExceptionHandler(MissingRequestCookieException.class)
    protected ResponseEntity<ExceptionResponse> handler(MissingRequestCookieException e) {
        ExceptionResponse response = ExceptionResponse.of(ExceptionCode.INVALID_COOKIE);
        return ResponseEntity.badRequest().body(response);
    }

    /**
     * JWT 관련 에러
     */
    //서명
    @ExceptionHandler(SignatureException.class)
    protected ResponseEntity<ExceptionResponse> handler(SignatureException e) {
        ExceptionResponse response = ExceptionResponse.of(ExceptionCode.INVALID_TOKEN);
        return ResponseEntity.badRequest().body(response);
    }

    //형식
    @ExceptionHandler(MalformedJwtException.class)
    protected ResponseEntity<ExceptionResponse> handler(MalformedJwtException e) {
        ExceptionResponse response = ExceptionResponse.of(ExceptionCode.WRONG_TOKEN);
        return ResponseEntity.badRequest().body(response);
    }

    //만료
    @ExceptionHandler(ExpiredJwtException.class)
    protected ResponseEntity<ExceptionResponse> handler(ExpiredJwtException e) {
        ExceptionResponse response = ExceptionResponse.of(ExceptionCode.EXPIRE_ACCESS_TOKEN);
        return ResponseEntity.badRequest().body(response);
    }

    @ExceptionHandler(Exception.class)
    protected ResponseEntity<ExceptionResponse> handler(Exception e) {
        ExceptionResponse response = ExceptionResponse.of(e);
        return ResponseEntity.badRequest().body(response);
    }
}

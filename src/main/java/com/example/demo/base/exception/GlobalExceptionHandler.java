package com.example.demo.base.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.authentication.BadCredentialsException;
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

    @ExceptionHandler(HttpMessageNotReadableException.class)
    protected ResponseEntity<ExceptionResponse> handler(HttpMessageNotReadableException e) {
        ExceptionResponse response = ExceptionResponse.of(ExceptionCode.INVALID_BODY);
        return ResponseEntity.badRequest().body(response);
    }
}

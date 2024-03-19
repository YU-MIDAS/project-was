package com.example.demo.base.exception;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Builder
@Getter
public class ExceptionResponse {
    private final LocalDateTime timestamp = LocalDateTime.now();
    private String type;
    private String message;

    public static ExceptionResponse of(ExceptionCode exceptionCode) {
        return ExceptionResponse.builder()
                .type(exceptionCode.name())
                .message(exceptionCode.getMessage())
                .build();
    }

    public static ExceptionResponse of(Exception exception) {
        return ExceptionResponse.builder()
                .type("SYSTEM_EXCEPTION")
                .message(exception.getClass().toString() + " : " + exception.getMessage())
                .build();
    }
}

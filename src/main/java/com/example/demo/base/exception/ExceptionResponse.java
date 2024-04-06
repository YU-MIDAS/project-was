package com.example.demo.base.exception;

import lombok.Builder;
import lombok.Getter;
import java.time.LocalDateTime;

@Builder
@Getter
public class ExceptionResponse {
    private final LocalDateTime timestamp = LocalDateTime.now();
    private String cause;
    private String message;

    public static ExceptionResponse of(ExceptionCode exceptionCode) {
        return of(exceptionCode.name(), exceptionCode.getMessage());
    }

    public static ExceptionResponse of(Exception exception) {
        return of(exception.getClass().getSimpleName(), exception.getMessage());
    }

    public static ExceptionResponse of(String type, String message) {
        return ExceptionResponse.builder()
                .cause(type)
                .message(message)
                .build();
    }
}

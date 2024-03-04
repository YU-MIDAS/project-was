package com.example.demo.base.exception;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Builder
@Getter
public class ExceptionResponse {
    private final LocalDateTime timestamp = LocalDateTime.now();
    private String type;
    private String title;
    private String message;

    public static ExceptionResponse of(ExceptionCode exceptionCode) {
        return ExceptionResponse.builder()
                .type(exceptionCode.getType())
                .title(exceptionCode.name())
                .message(exceptionCode.getMessage())
                .build();
    }
}

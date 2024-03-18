package com.example.demo.base.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ExceptionCode {
    //access
    EMPTY_TOKEN("TOKEN", HttpStatus.BAD_REQUEST, "헤더에 토큰이 존재하지 않습니다."),
    INVALID_TOKEN("TOKEN", HttpStatus.UNAUTHORIZED, "유효하지 않은 토큰입니다."),
    WRONG_TOKEN("TOKEN", HttpStatus.UNAUTHORIZED, "올바르지 않은 토큰 형식입니다."),
    EXPIRE_ACCESS_TOKEN("TOKEN", HttpStatus.UNAUTHORIZED, "만료된 토큰 입니다."),

    //refresh
    EXPIRE_REFRESH_TOKEN("TOKEN", HttpStatus.UNAUTHORIZED, "만료된 토큰 입니다. 재로그인 필요"),
    TOKEN_NOT_FOUND("TOKEN", HttpStatus.UNAUTHORIZED, "해당 토큰을 찾을 수 없습니다. 재로그인 필요"),

    //request
    INVALID_BODY("BODY", HttpStatus.BAD_REQUEST, "올바르지 않은 바디 값 입니다."),
    INVALID_HEADER("HEADER", HttpStatus.BAD_REQUEST, "올바르지 않은 헤더 값 입니다."),
    INVALID_COOKIE("COOKIE", HttpStatus.BAD_REQUEST, "올바르지 않은 쿠키 값 입니다."),

    ACCOUNT_NOT_FOUND("AUTH", HttpStatus.BAD_REQUEST, "해당 계정이름을 찾을 수 없습니다."),
    INVALID_PASSWORD("AUTH", HttpStatus.UNAUTHORIZED, "유효하지 않은 비밀번호 입니다."),
    BLACK_LIST("AUTH", HttpStatus.BAD_REQUEST, "무효화된 토큰입니다."),
    INVALID_SIGN_OUT("AUTH", HttpStatus.BAD_REQUEST, "비정상적인 로그아웃 시도입니다."),

    DUPLICATE_NAME("CREATE", HttpStatus.BAD_REQUEST, "중복된 이름이 존재합니다.");

    private String type;
    private HttpStatus status;
    private String message;

    ExceptionCode(String type, HttpStatus status, String message) {
        this.type = type;
        this.status = status;
        this.message = message;
    }
}

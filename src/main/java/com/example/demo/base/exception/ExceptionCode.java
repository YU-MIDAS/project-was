package com.example.demo.base.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ExceptionCode {
    //access
    INVALID_TOKEN(HttpStatus.UNAUTHORIZED, "유효하지 않은 서명의 토큰입니다."),
    WRONG_TOKEN(HttpStatus.UNAUTHORIZED, "올바르지 않은 토큰 형식입니다."),
    EXPIRE_ACCESS_TOKEN(HttpStatus.UNAUTHORIZED, "만료된 토큰 입니다."),

    //refresh
    EXPIRE_REFRESH_TOKEN(HttpStatus.UNAUTHORIZED, "만료된 토큰 입니다. 재로그인 필요"),
    TOKEN_NOT_FOUND(HttpStatus.UNAUTHORIZED, "해당 토큰을 찾을 수 없습니다. 재로그인 필요"),

    //request
    INVALID_BODY(HttpStatus.BAD_REQUEST, "올바르지 않은 바디 값 입니다."),
    INVALID_HEADER(HttpStatus.BAD_REQUEST, "올바르지 않은 헤더 값 입니다."),
    INVALID_COOKIE(HttpStatus.BAD_REQUEST, "올바르지 않은 쿠키 값 입니다."),

    ACCOUNT_NOT_FOUND(HttpStatus.BAD_REQUEST, "해당 계정이름을 찾을 수 없습니다."),
    INVALID_PASSWORD(HttpStatus.UNAUTHORIZED, "유효하지 않은 비밀번호 입니다."),
    BLACK_LIST(HttpStatus.BAD_REQUEST, "무효화된 토큰입니다."),
    INVALID_SIGN_OUT(HttpStatus.BAD_REQUEST, "비정상적인 로그아웃 시도입니다."),

    DUPLICATE_NAME(HttpStatus.BAD_REQUEST, "중복된 이름이 존재합니다."),
    EMPTY_LOCATION(HttpStatus.BAD_REQUEST, "위치정보가 존재하지 않습니다.");

    private HttpStatus status;
    private String message;

    ExceptionCode(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
    }
}

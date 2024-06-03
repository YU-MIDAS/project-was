package com.example.demo.bounded_context.auth.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

public record SignInAccountRequest(
        @NotBlank(message = "아이디를 입력해주세요.")
        String accountName,
        @NotBlank(message = "비밀번호를 입력해주세요.")
        String password
) {

}

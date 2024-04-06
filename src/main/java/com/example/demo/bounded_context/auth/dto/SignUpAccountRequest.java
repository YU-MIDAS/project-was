package com.example.demo.bounded_context.auth.dto;

import com.example.demo.bounded_context.account.entity.Account;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class SignUpAccountRequest {
    @NotBlank(message = "아이디를 입력해주세요.")
    String accountName;

    @NotBlank(message = "비밀번호를 입력해주세요.")
    String password;

    @NotBlank(message = "이메일을 입력해주세요.")
    @Email(message = "유효한 이메일 주소를 입력해주세요.")
    String email;

    String nickname;
    Double latitude;
    Double longitude;
}

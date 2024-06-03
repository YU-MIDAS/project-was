package com.example.demo.bounded_context.auth.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

public record SignUpAccountRequest(
        @NotBlank(message = "아이디를 입력해주세요.")
        String accountName,

        @NotBlank(message = "비밀번호를 입력해주세요.")
        String password,

        @NotBlank(message = "이메일을 입력해주세요.")
        @Email(message = "유효한 이메일 주소를 입력해주세요.")
        String email,

        String nickname,
        Double latitude,
        Double longitude
) {
        @Builder
        public SignUpAccountRequest(String accountName, String password, String email, String nickname, Double latitude, Double longitude) {
                this.accountName = accountName;
                this.password = password;
                this.email = email;
                this.nickname = nickname;
                this.latitude = latitude;
                this.longitude = longitude;
        }
}
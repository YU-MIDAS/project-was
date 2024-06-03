package com.example.demo.bounded_context.account.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record AccountUpdateRequest(
        @NotBlank(message = "이메일을 입력해주세요.")
        @Email(message = "유효한 이메일 주소를 입력해주세요.")
        String email,
        String nickname,
        Double latitude,
        Double longitude
) {
}

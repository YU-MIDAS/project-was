package com.example.demo.bounded_context.auth.dto;

import lombok.Getter;

@Getter
public class SignUpUserRequest {
    String username;
    String password;
    String email;
}

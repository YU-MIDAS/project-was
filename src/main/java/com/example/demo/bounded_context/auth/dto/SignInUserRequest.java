package com.example.demo.bounded_context.auth.dto;

import lombok.Getter;

@Getter
public class SignInUserRequest {
    String username;
    String password;
}

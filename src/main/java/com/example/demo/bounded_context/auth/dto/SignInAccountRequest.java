package com.example.demo.bounded_context.auth.dto;

import lombok.Getter;

@Getter
public class SignInAccountRequest {
    String accountName;
    String password;
}

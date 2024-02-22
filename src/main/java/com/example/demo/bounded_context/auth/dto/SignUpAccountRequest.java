package com.example.demo.bounded_context.auth.dto;

import lombok.Getter;

@Getter
public class SignUpAccountRequest {
    String accountName;
    String password;
    String email;
}

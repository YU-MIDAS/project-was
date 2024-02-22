package com.example.demo.bounded_context.account.controller;

import com.example.demo.bounded_context.account.entity.Account;
import com.example.demo.bounded_context.account.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/account")
public class AccountController {
    private final AccountService accountService;

    @GetMapping("/list")
    public List<Account> list(){
        return accountService.list();
    }
}

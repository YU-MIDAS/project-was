package com.example.demo.bounded_context.account.service;

import com.example.demo.bounded_context.account.entity.Account;
import com.example.demo.bounded_context.account.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AccountService {
    private final AccountRepository accountRepository;

    public Account read(String accountName){
        return accountRepository.findByAccountName(accountName)
                .orElseThrow(() -> new IllegalArgumentException("해당 계정을 찾을 수 없습니다. : " + accountName));
    }

    public List<Account> list(){
        return accountRepository.findAll();
    }
}

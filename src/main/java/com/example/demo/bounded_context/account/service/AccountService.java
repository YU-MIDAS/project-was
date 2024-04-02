package com.example.demo.bounded_context.account.service;

import com.example.demo.base.Resolver.AccessToken;
import com.example.demo.base.blacklist_token.BlacklistTokenService;
import com.example.demo.base.exception.CustomException;
import com.example.demo.base.exception.ExceptionCode;
import com.example.demo.base.refresh_token.RefreshToken;
import com.example.demo.base.refresh_token.RefreshTokenRepository;
import com.example.demo.base.refresh_token.RefreshTokenService;
import com.example.demo.bounded_context.account.entity.Account;
import com.example.demo.bounded_context.account.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AccountService {
    private final AccountRepository accountRepository;
    private final RefreshTokenService refreshTokenService;
    private final RefreshTokenRepository refreshTokenRepository;
    private final BlacklistTokenService blacklistTokenService;

    public Account read(String accountName){ //loadByUsername
        return accountRepository.findByAccountName(accountName)
                .orElseThrow(() -> new CustomException(ExceptionCode.ACCOUNT_NOT_FOUND));
    }

    public Account read(Long accountName){
        return accountRepository.findById(accountName)
                .orElseThrow(() -> new CustomException(ExceptionCode.ACCOUNT_NOT_FOUND));
    }

    public List<Account> list(){
        return accountRepository.findAll();
    }

    /**
     * access / refresh token 로 로그아웃
     */
    public void signOut(AccessToken accessToken, String refreshToken){
        RefreshToken refresh = refreshTokenService.read(refreshToken);
        if(refresh.getUserId() != accessToken.getAccountId()){
            throw new CustomException(ExceptionCode.INVALID_SIGN_OUT);
        }
        blacklistTokenService.create(accessToken);
        refreshTokenRepository.delete(refresh);
    }
}

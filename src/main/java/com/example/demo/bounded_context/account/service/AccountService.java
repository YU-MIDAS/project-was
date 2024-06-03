package com.example.demo.bounded_context.account.service;

import com.example.demo.base.Resolver.AccessToken;
import com.example.demo.base.blacklist_token.BlacklistTokenService;
import com.example.demo.base.exception.CustomException;
import com.example.demo.base.exception.ExceptionCode;
import com.example.demo.base.refresh_token.RefreshToken;
import com.example.demo.base.refresh_token.RefreshTokenService;
import com.example.demo.bounded_context.account.dto.AccountUpdateRequest;
import com.example.demo.bounded_context.account.entity.Account;
import com.example.demo.bounded_context.account.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AccountService {
    private final AccountRepository accountRepository;
    private final RefreshTokenService refreshTokenService;
    private final BlacklistTokenService blacklistTokenService;

    @Transactional(readOnly = true)
    public Account findByAccountName(String accountName){ //loadByUsername
        return accountRepository.findByAccountName(accountName)
                .orElseThrow(() -> new CustomException(ExceptionCode.ACCOUNT_NOT_FOUND));
    }

    @Transactional(readOnly = true)
    public Account findByAccountId(Long accountId){
        return accountRepository.findById(accountId)
                .orElseThrow(() -> new CustomException(ExceptionCode.ACCOUNT_NOT_FOUND));
    }

    /**
     * 로그아웃
     * 1. access token 정보로 blacklist token 생성
     * 2. refresh token 삭제
     */
    @Transactional
    public void signOut(AccessToken accessToken, String refreshToken){
        RefreshToken refresh = validateRefreshTokenOwnership(accessToken, refreshToken);
        blacklistTokenService.create(accessToken);
        refreshTokenService.remove(refresh.getAccountId());
    }

    /**
     * 회원탈퇴
     * 1. refresh token 삭제
     * 2. account 삭제 - account 를 조회할 수 없으므로 access token 정보로 blacklist token 생성하지 않아도 된다.
     */
    @Transactional
    public void withdrawal(AccessToken accessToken, String refreshToken){
        RefreshToken refresh = validateRefreshTokenOwnership(accessToken, refreshToken);
        refreshTokenService.remove(refresh.getAccountId());
        accountRepository.deleteById(refresh.getAccountId());
    }

    @Transactional
    public Account update(Long accountId, AccountUpdateRequest request){
        Account foundUser = findByAccountId(accountId);
        foundUser.update(request);
        return foundUser;
    }

    @Transactional(readOnly = true)
    public RefreshToken validateRefreshTokenOwnership(AccessToken accessToken, String refreshToken){
        RefreshToken refresh = refreshTokenService.read(refreshToken);
        if(!refresh.getAccountId().equals(accessToken.getAccountId())){
            throw new CustomException(ExceptionCode.INVALID_SIGN_OUT);
        }
        return refresh;
    }
}

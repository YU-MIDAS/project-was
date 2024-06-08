package com.example.demo.bounded_context.account.service;

import com.example.demo.base.Resolver.AccessToken;
import com.example.demo.base.blacklist_token.BlacklistTokenService;
import com.example.demo.base.refresh_token.RefreshToken;
import com.example.demo.base.refresh_token.RefreshTokenService;
import com.example.demo.bounded_context.account.dto.AccountResponse;
import com.example.demo.bounded_context.account.dto.AccountUpdateRequest;
import com.example.demo.bounded_context.account.entity.Account;
import com.example.demo.bounded_context.wiki.service.WikiService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
public class AccountUseCase {

    private final AccountService accountService;
    private final RefreshTokenService refreshTokenService;
    private final BlacklistTokenService blacklistTokenService;
    private final WikiService wikiService;

    @Transactional(readOnly = true)
    public AccountResponse read(Long accountId){
        log.info("사용자 정보 조회");
        Account foundAccount = accountService.findByAccountId(accountId);
        return AccountResponse.fromEntity(foundAccount);
    }

    /**
     * 로그아웃
     * 1. access token 정보로 blacklist token 생성
     * 2. refresh token 삭제
     */
    @Transactional
    public void signOut(AccessToken accessToken, String refreshToken){
        RefreshToken refresh = refreshTokenService.validateRefreshTokenOwnership(accessToken, refreshToken);
        blacklistTokenService.create(accessToken);
        refreshTokenService.delete(refresh.getAccountId());
    }

    /**
     * 회원탈퇴
     * 1. refresh token 삭제
     * 2. account 삭제 - account 를 조회할 수 없으므로 access token 정보로 blacklist token 생성하지 않아도 된다.
     */
    @Transactional
    public void withdrawal(AccessToken accessToken, String refreshToken){
        log.info("회원탈퇴");
        RefreshToken refresh = refreshTokenService.validateRefreshTokenOwnership(accessToken, refreshToken);
        Account foundAccount = accountService.findByAccountId(refresh.getAccountId());

        wikiService.updateWriter(foundAccount);
        refreshTokenService.delete(refresh.getAccountId());
        accountService.delete(foundAccount);
    }

    @Transactional
    public Long update(Long accountId, AccountUpdateRequest request){
        Account updateAccount = accountService.update(accountId, request);
        return updateAccount.getId();
    }
}

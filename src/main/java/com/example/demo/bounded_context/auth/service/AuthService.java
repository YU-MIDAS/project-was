package com.example.demo.bounded_context.auth.service;

import com.example.demo.base.jwt.JwtProvider;
import com.example.demo.base.refresh_token.RefreshToken;
import com.example.demo.base.refresh_token.RefreshTokenService;
import com.example.demo.bounded_context.auth.dto.SignInAccountRequest;
import com.example.demo.bounded_context.auth.dto.SignUpAccountRequest;
import com.example.demo.bounded_context.auth.dto.TokenResponse;
import com.example.demo.bounded_context.account.entity.Account;
import com.example.demo.bounded_context.account.repository.AccountRepository;
import com.example.demo.bounded_context.auth.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final PasswordEncoder passwordEncoder;
    private final AccountRepository accountRepository;
    private final JwtProvider jwtProvider;
    private final RefreshTokenService refreshTokenService;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;

    public Account signUp(@RequestBody SignUpAccountRequest request) throws Exception{
        isDuplicated(request.getAccountName());

        Account account = Account.builder()
                .accountName(request.getAccountName())
                .password(passwordEncoder.encode(request.getPassword()))
                .email(request.getEmail())
                .build();

        return accountRepository.save(account);
    }

    /**
     * username/password 로그인 메서드
     */
    public TokenResponse signIn(@RequestBody SignInAccountRequest request) throws Exception{
        User user = authenticate(request);
        String accessToken = jwtProvider.generatorAccessToken(user.getId());
        String refreshToken = refreshTokenService.issueRefreshToken(user.getId());

        return TokenResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    /**
     * refresh token 로 access / refresh token 발급
     */
    public TokenResponse reIssueToken(String token) throws Exception{
        RefreshToken refreshToken = refreshTokenService.reIssueRefreshToken(token);
        String accessToken = jwtProvider.generatorAccessToken(refreshToken.getUserId());

        return TokenResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken.getToken())
                .build();
    }

    public void isDuplicated(String accountName) throws Exception {
        if (accountRepository.findByAccountName(accountName).isPresent()){
            throw new Exception("중복된 계정이름이 존재합니다.");
        }
    }


    /**
     * 인증 메서드
     */
    public User authenticate(SignInAccountRequest request) throws Exception{
        //인증 객체 셍성 : Authentication 구현 객체 UsernamePasswordAuthenticationToken
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(request.getAccountName(), request.getPassword());

        //인증 : CustomUserDetailsService - loadUserByUsername 사용
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

        //UserDetails 객체 반환
        return (User) authentication.getPrincipal();
    }
}

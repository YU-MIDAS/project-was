package com.example.demo.base.refresh_token;

import com.example.demo.base.Resolver.AccessToken;
import com.example.demo.base.exception.CustomException;
import com.example.demo.base.exception.ExceptionCode;
import com.example.demo.base.jwt.JwtProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RefreshTokenService {
    private final RefreshTokenRepository refreshTokenRepository;
    private final JwtProperties jwtProperties;

    @Transactional(readOnly = true)
    public RefreshToken findByToken(String token){
        return refreshTokenRepository.findByToken(token)
                .orElseThrow(() -> new CustomException(ExceptionCode.TOKEN_NOT_FOUND));
    }

    @Transactional(readOnly = true)
    public RefreshToken findByAccountId(Long accountId){
        return refreshTokenRepository.findByAccountId(accountId)
                .orElseThrow(() -> new CustomException(ExceptionCode.TOKEN_NOT_FOUND));
    }

    @Transactional
    public RefreshToken delete(Long accountId){
        RefreshToken refreshToken = findByAccountId(accountId);
        refreshTokenRepository.delete(refreshToken);
        return refreshToken;
    }

    @Transactional(readOnly = true)
    public void isExpired(RefreshToken refreshToken){
        if(refreshToken.getTimeToLive().before(new Date())){
            throw new CustomException(ExceptionCode.EXPIRE_REFRESH_TOKEN);
        }
    }

    /**
     * refresh token 발급
     */
    @Transactional
    public RefreshToken issueRefreshToken(Long id){
        Date now = new Date();
        final RefreshToken refreshToken = RefreshToken.builder()
                .accountId(id)
                .token(UUID.randomUUID().toString())
                .timeToLive(new Date(now.getTime() + jwtProperties.getRefreshExpiration()))
                .build();
        return refreshTokenRepository.save(refreshToken);
    }

    /**
     * Refresh Token Rotation(RTR) - 기존 refresh token 으로 refresh token 재발급
     */
    @Transactional
    public RefreshToken reIssueRefreshToken(String token){
        Date now = new Date();
        RefreshToken refreshToken = findByToken(token);
        isExpired(refreshToken);
        refreshToken.reIssueToken(UUID.randomUUID().toString(),
                new Date(now.getTime() + jwtProperties.getRefreshExpiration()));
        return refreshTokenRepository.save(refreshToken);
    }

    @Transactional(readOnly = true)
    public RefreshToken validateRefreshTokenOwnership(AccessToken accessToken, String refreshToken){
        RefreshToken refresh = findByToken(refreshToken);
        if(!refresh.getAccountId().equals(accessToken.getAccountId())){
            throw new CustomException(ExceptionCode.INVALID_SIGN_OUT);
        }
        return refresh;
    }
}

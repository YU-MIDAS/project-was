package com.example.demo.base.refresh_token;

import com.example.demo.base.exception.CustomException;
import com.example.demo.base.exception.ExceptionCode;
import com.example.demo.base.jwt.JwtProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RefreshTokenService {
    private final RefreshTokenRepository refreshTokenRepository;
    private final JwtProperties jwtProperties;

    public RefreshToken read(String token){
        return refreshTokenRepository.findByToken(token)
                .orElseThrow(() -> new CustomException(ExceptionCode.TOKEN_NOT_FOUND));
    }

    public void isExpired(RefreshToken refreshToken){
        if(refreshToken.getTimeToLive().before(new Date())){
            throw new CustomException(ExceptionCode.EXPIRE_REFRESH_TOKEN);
        }
    }

    /**
     * refresh token 발급
     */
    public RefreshToken issueRefreshToken(Long id){
        Date now = new Date();
        final RefreshToken refreshToken = RefreshToken.builder()
                .userId(id)
                .token(UUID.randomUUID().toString())
                .timeToLive(new Date(now.getTime() + jwtProperties.getRefreshExpiration()))
                .build();
        return refreshTokenRepository.save(refreshToken);
    }

    /**
     * Refresh Token Rotation(RTR) - 기존 refresh token 으로 refresh token 재발급
     */
    public RefreshToken reIssueRefreshToken(String token){
        Date now = new Date();
        RefreshToken refreshToken = read(token);
        isExpired(refreshToken);
        refreshToken.reIssueToken(UUID.randomUUID().toString(),
                new Date(now.getTime() + jwtProperties.getRefreshExpiration()));
        return refreshTokenRepository.save(refreshToken);
    }
}

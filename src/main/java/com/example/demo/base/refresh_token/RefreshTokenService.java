package com.example.demo.base.refresh_token;

import com.example.demo.base.jwt.JwtProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RefreshTokenService {
    private final RefreshTokenRepository refreshTokenRepository;
    private final JwtProperties jwtProperties;

    public RefreshToken read(String token){
        return refreshTokenRepository.findByToken(token)
                .orElseThrow(() -> new IllegalArgumentException("해당 토큰을 찾을 수 없습니다. : " + token));
    }

    /**
     * refresh token 발급
     */
    public String issueRefreshToken(Long id){
        final RefreshToken refreshToken = RefreshToken.builder()
                .userId(id)
                .token(UUID.randomUUID().toString())
                .timeToLive(jwtProperties.getRefreshExpiration())
                .build();
        refreshTokenRepository.save(refreshToken);
        return refreshToken.getToken();
    }

    /**
     * Refresh Token Rotation(RTR) - 기존 refresh token 으로 refresh token 재발급
     */
    public RefreshToken reIssueRefreshToken(String token) throws Exception{
        RefreshToken refreshToken = read(token);
        refreshToken.reIssueToken(UUID.randomUUID().toString(), jwtProperties.getRefreshExpiration());
        return refreshTokenRepository.save(refreshToken);
    }

}

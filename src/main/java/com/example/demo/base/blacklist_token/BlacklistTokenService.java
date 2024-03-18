package com.example.demo.base.blacklist_token;

import com.example.demo.base.exception.CustomException;
import com.example.demo.base.exception.ExceptionCode;
import com.example.demo.base.jwt.JwtProvider;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BlacklistTokenService {

    final JwtProvider jwtProvider;
    final BlacklistTokenRepository blackListTokenRepository;

    public void checkBlacklist(String accessToken){
        if(blackListTokenRepository.existsById(accessToken)) {
            throw new CustomException(ExceptionCode.BLACK_LIST);
        }
    }

    public BlacklistToken create(String accessToken){
        Claims claims = jwtProvider.getValidToken(accessToken);
        BlacklistToken blacklistToken = BlacklistToken.builder()
                .invalidAccessToken(accessToken)
                .timeToLive(claims.getExpiration())
                .build();
        return blackListTokenRepository.save(blacklistToken);
    }
}

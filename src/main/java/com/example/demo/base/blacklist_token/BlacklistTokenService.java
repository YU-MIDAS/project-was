package com.example.demo.base.blacklist_token;

import com.example.demo.base.Resolver.AccessToken;
import com.example.demo.base.exception.CustomException;
import com.example.demo.base.exception.ExceptionCode;
import com.example.demo.base.jwt.JwtProvider;
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

    public BlacklistToken create(AccessToken accessToken){
        BlacklistToken blacklistToken = BlacklistToken.of(accessToken);
        return blackListTokenRepository.save(blacklistToken);
    }
}

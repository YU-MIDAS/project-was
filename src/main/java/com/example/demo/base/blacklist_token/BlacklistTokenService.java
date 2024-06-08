package com.example.demo.base.blacklist_token;

import com.example.demo.base.Resolver.AccessToken;
import com.example.demo.base.exception.CustomException;
import com.example.demo.base.exception.ExceptionCode;
import com.example.demo.base.jwt.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class BlacklistTokenService {

    final JwtProvider jwtProvider;
    final BlacklistTokenRepository blackListTokenRepository;

    @Transactional(readOnly = true)
    public void checkBlacklist(String accessToken){
        if(blackListTokenRepository.existsById(accessToken)) {
            throw new CustomException(ExceptionCode.BLACK_LIST);
        }
    }

    @Transactional
    public BlacklistToken create(AccessToken accessToken){
        BlacklistToken blacklistToken = BlacklistToken.of(accessToken);
        return blackListTokenRepository.save(blacklistToken);
    }
}

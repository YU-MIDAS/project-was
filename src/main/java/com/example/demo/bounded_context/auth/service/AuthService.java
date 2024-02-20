package com.example.demo.bounded_context.auth.service;

import com.example.demo.base.jwt.JwtProvider;
import com.example.demo.bounded_context.auth.dto.SignInUserRequest;
import com.example.demo.bounded_context.auth.dto.SignUpUserRequest;
import com.example.demo.bounded_context.auth.dto.TokenResponse;
import com.example.demo.bounded_context.user.entity.User;
import com.example.demo.bounded_context.user.repository.UserRepository;
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
    private final UserRepository userRepository;
    private final JwtProvider jwtProvider;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;

    public User signUp(@RequestBody SignUpUserRequest request) throws Exception{
        isDuplicated(request.getUsername());

        User user = User.builder()
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .email(request.getEmail())
                .build();

        return userRepository.save(user);
    }

    public void isDuplicated(String username) throws Exception {
        if (userRepository.findByUsername(username).isPresent()){
            throw new Exception("중복된 아이디가 존재합니다.");
        }
    }

    /**
     * username/password 로그인 메서드
     * @param request
     * @return
     * @throws Exception
     */
    public TokenResponse authenticate(@RequestBody SignInUserRequest request) throws Exception{
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword());
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

        String accessToken = jwtProvider.generatorAccessToken(authentication);
        String refreshToken = jwtProvider.generatorRefreshToken(authentication);

        return TokenResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }
}

package com.example.demo.bounded_context.auth.service;

import com.example.demo.base.jwt.JwtProvider;
import com.example.demo.base.refresh_token.RefreshTokenService;
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
    private final RefreshTokenService refreshTokenService;
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
     */
    public TokenResponse signIn(@RequestBody SignInUserRequest request) throws Exception{
        User user = (User) authenticate(request);
        String accessToken = jwtProvider.generatorAccessToken(user.getUsername());
        String refreshToken = refreshTokenService.issueRefreshToken(user.getId());

        return TokenResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    /**
     * 인증 메서드
     */
    public Object authenticate(SignInUserRequest request) throws Exception{
        //인증 객체 셍성 : Authentication 구현 객체 UsernamePasswordAuthenticationToken
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword());

        //인증 : CustomUserDetailsService - loadUserByUsername 사용
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

        //UserDetails 구현 객체 반환
        return authentication.getPrincipal();
    }

}

package com.example.demo.bounded_context.auth.service;

import com.example.demo.bounded_context.auth.dto.SignUpUserRequest;
import com.example.demo.bounded_context.user.entity.User;
import com.example.demo.bounded_context.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

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
}

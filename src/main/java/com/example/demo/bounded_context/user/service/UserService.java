package com.example.demo.bounded_context.user.service;

import com.example.demo.bounded_context.user.entity.User;
import com.example.demo.bounded_context.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public List<User> list(){
        return userRepository.findAll();
    }

    public ResponseEntity<String> register(@RequestBody User user) {
        User savedUser = null;
        ResponseEntity response = null;

        try{
            String hashPwd = passwordEncoder.encode(user.getPassword());
            user.setPassword(hashPwd);
            savedUser = userRepository.save(user);
            if(savedUser.getId() > 0){
                response = ResponseEntity
                        .status(HttpStatus.CREATED)
                        .body("회원가입이 성공했습니다.");
            }
        }catch (Exception e){
            response = ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("회원가입이 실패했습니다. " + e.getMessage());
        }

        return response;
    }
}

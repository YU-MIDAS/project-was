package com.example.demo.bounded_context.user.service;

import com.example.demo.bounded_context.user.entity.User;
import com.example.demo.bounded_context.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public User read(String username){
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("해당 유저를 찾을 수 없습니다. : " + username));
    }

    public List<User> list(){
        return userRepository.findAll();
    }
}

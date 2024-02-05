package com.example.demo.bounded_context.user.controller;

import com.example.demo.bounded_context.user.entity.User;
import com.example.demo.bounded_context.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserController {
    private final UserService userService;

    @GetMapping("")
    public List<User> list(){
        return userService.list();
    }

    @GetMapping("/new")
    public ResponseEntity<String> test(){
        return ResponseEntity.ok("success");
    }
}

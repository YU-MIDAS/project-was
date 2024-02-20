package com.example.demo.bounded_context.user.controller;

import com.example.demo.bounded_context.user.entity.User;
import com.example.demo.bounded_context.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserController {
    private final UserService userService;

    @GetMapping("/list")
    public List<User> list(){
        return userService.list();
    }
}

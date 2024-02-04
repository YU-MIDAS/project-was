package com.example.demo.bounded_context.user.repository;

import com.example.demo.bounded_context.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}

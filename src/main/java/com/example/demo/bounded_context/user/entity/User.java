package com.example.demo.bounded_context.user.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class User implements UserDetails {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String username;

    private String password;

    private String email;

    @Builder
    public User(String username, String password, String email) {
        this.username = username;
        this.password = password;
        this.email = email;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null; //계정의 권한 목록
    }
    @Override
    public boolean isAccountNonExpired() {
        return false; //계정이 만료되지 않음
    }
    @Override
    public boolean isAccountNonLocked() {
        return true; //계정이 잠겨있지 않음
    }
    @Override
    public boolean isCredentialsNonExpired() {
        return true; //계정의 비밀번호 만료되지 않음
    }
    @Override
    public boolean isEnabled() {
        return true; //계정 비활성화 상태가 아님
    }
}


package com.example.demo.bounded_context.auth.entity;

import com.example.demo.bounded_context.account.entity.Account;
import lombok.Builder;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

@Getter
public class User implements UserDetails {

    private final Long id;
    private final String username;
    private final String password;
    private final Collection<? extends GrantedAuthority> authorities;

    @Builder
    public User(Long id, String username, String password, Collection<? extends GrantedAuthority> authorities) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.authorities = authorities;
    }

    public static User of(Account account){
        return User.builder()
                .id(account.getId())
                .username(account.getAccountName())
                .password(account.getPassword())
                .authorities(null)
                .build();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() { return authorities; }//계정의 권한 목록

    @Override
    public String getPassword() { return password; }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true; //계정이 만료되지 않음
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

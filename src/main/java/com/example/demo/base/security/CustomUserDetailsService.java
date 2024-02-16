package com.example.demo.base.security;

import com.example.demo.bounded_context.user.entity.User;
import com.example.demo.bounded_context.user.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        String loadUsername, loadPassword;
        List<GrantedAuthority> authorities;

        Optional<User> user = userRepository.findByUsername(username);
        if(user.isPresent()){
            loadUsername = user.get().getUsername();
            loadPassword = user.get().getPassword();
            authorities = new ArrayList<>();
            authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
            return new org.springframework.security.core.userdetails.User(loadUsername, loadPassword, authorities);
        }
        return null;
    }
}

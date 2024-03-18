package com.example.demo.bounded_context.account.entity;


import com.example.demo.base.common.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Account extends BaseTimeEntity {
    @Column(nullable = false)
    private String accountName;

    private String password;

    private String email;

    private String nickname;

    private String region;

    @Builder
    public Account(String accountName, String password, String email, String nickname, String region) {
        this.accountName = accountName;
        this.password = password;
        this.email = email;
        this.nickname = nickname;
        this.region = region;
    }
}


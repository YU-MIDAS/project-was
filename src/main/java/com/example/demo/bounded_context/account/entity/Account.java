package com.example.demo.bounded_context.account.entity;


import com.example.demo.base.common.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Account extends BaseTimeEntity {
    @Column(nullable = false, unique = true)
    private String accountName;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false, unique = true)
    private String email;

    private String nickname;

    private String state;

    private String city;

    private Double latitude;

    private Double longitude;

    @Builder
    public Account(String accountName, String password, String email, String nickname, String state, String city, Double latitude, Double longitude) {
        this.accountName = accountName;
        this.password = password;
        this.email = email;
        this.nickname = nickname;
        this.state = state;
        this.city = city;
        this.latitude = latitude;
        this.longitude = longitude;
    }
}


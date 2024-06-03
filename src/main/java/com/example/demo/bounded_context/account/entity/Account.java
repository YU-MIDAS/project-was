package com.example.demo.bounded_context.account.entity;


import com.example.demo.base.common.BaseTimeEntity;
import com.example.demo.bounded_context.account.dto.AccountUpdateRequest;
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

    @Column(nullable = false)
    private String email;

    private String nickname;

    private Double latitude;

    private Double longitude;

    @Builder
    public Account(String accountName, String password, String email, String nickname, Double latitude, Double longitude) {
        this.accountName = accountName;
        this.password = password;
        this.email = email;
        this.nickname = nickname;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public void update(AccountUpdateRequest request){
        this.email = request.email();
        this.nickname = request.nickname();
        this.latitude = request.latitude();
        this.longitude = request.longitude();
    }
}


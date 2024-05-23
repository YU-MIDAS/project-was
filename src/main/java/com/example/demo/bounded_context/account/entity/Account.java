package com.example.demo.bounded_context.account.entity;


import com.example.demo.base.common.BaseTimeEntity;
import com.example.demo.bounded_context.account.dto.AccountRequest;
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

//    @Column(nullable = false)
    private Double latitude;

//    @Column(nullable = false)
    private Double longitude;

    @Builder
    public Account(String accountName, String password, String email, String nickname, Double latitude, Double longitude) {
        this.accountName = accountName;
        this.password = password;
        this.email = email;
        this.nickname = nickname;
        this.latitude = latitude == null ? 35.8704 : latitude;
        this.longitude = longitude == null ? 128.5564 : longitude;
    }

    public void update(AccountRequest request){
        this.email = request.getEmail();
        this.nickname = request.getNickname();
        this.latitude = request.getLatitude();
        this.longitude = request.getLongitude();
    }
}


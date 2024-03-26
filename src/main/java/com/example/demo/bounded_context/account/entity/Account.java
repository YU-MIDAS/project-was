package com.example.demo.bounded_context.account.entity;


import com.example.demo.base.common.BaseTimeEntity;
import com.example.demo.bounded_context.account.dto.PatchAccountRequest;
import jakarta.persistence.*;
import lombok.*;

import javax.sound.midi.Patch;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Account extends BaseTimeEntity {
    @Column(nullable = false, unique = true)
    private String accountName;
    @Column(nullable = false)
    private String password;
    @Column(nullable = false, unique = true)
    private String email;
    @Column(nullable = false, unique = true)
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

    public Account update(PatchAccountRequest requestDto){
        this.password = requestDto.getPassword();
        this.email = requestDto.getEmail();
        this.nickname = requestDto.getNickname();
        this.region = requestDto.getRegion();
        return this;
    }
}


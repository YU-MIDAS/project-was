package com.example.demo.bounded_context.account.dto;

import com.example.demo.bounded_context.account.entity.Account;
import lombok.Builder;
import lombok.Getter;

@Getter
public class AccountResponse {
    String accountName;
    String email;
    String nickname;
    Double latitude;
    Double longitude;

    @Builder
    public AccountResponse(String accountName, String email, String nickname, Double latitude, Double longitude) {
        this.accountName = accountName;
        this.email = email;
        this.nickname = nickname;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public static AccountResponse fromEntity(Account account){
        return AccountResponse.builder()
                .accountName(account.getAccountName())
                .email(account.getEmail())
                .nickname(account.getNickname())
                .latitude(account.getLatitude())
                .longitude(account.getLongitude())
                .build();
    }
}

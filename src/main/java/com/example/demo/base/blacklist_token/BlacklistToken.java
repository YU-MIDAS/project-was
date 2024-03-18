package com.example.demo.base.blacklist_token;

import com.example.demo.base.Resolver.AccessToken;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;

@Getter
@Entity
@Builder
@NoArgsConstructor
public class BlacklistToken {
    @Id
    private String invalidAccessToken;
    private Long accountId;
    private Date timeToLive;

    public BlacklistToken(String invalidAccessToken, Long accountId, Date timeToLive) {
        this.invalidAccessToken = invalidAccessToken;
        this.accountId = accountId;
        this.timeToLive = timeToLive;
    }

    public static BlacklistToken of(AccessToken accessToken){
        return BlacklistToken.builder()
                .invalidAccessToken(accessToken.getToken())
                .accountId(accessToken.getAccountId())
                .timeToLive(accessToken.getTimeToLive())
                .build();
    }
}

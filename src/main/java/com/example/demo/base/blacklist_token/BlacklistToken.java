package com.example.demo.base.blacklist_token;

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
    private Date timeToLive;

    public BlacklistToken(String invalidAccessToken, Date timeToLive) {
        this.invalidAccessToken = invalidAccessToken;
        this.timeToLive = timeToLive;
    }
}

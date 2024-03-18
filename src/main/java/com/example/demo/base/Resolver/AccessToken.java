package com.example.demo.base.Resolver;

import lombok.Builder;
import lombok.Getter;

import java.util.Date;

@Builder
@Getter
public class AccessToken {
    String token;
    Long accountId;
    Date timeToLive;

    public AccessToken(String token, Long accountId, Date timeToLive) {
        this.token = token;
        this.accountId = accountId;
        this.timeToLive = timeToLive;
    }
}

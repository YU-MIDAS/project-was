package com.example.demo.base.refresh_token;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Setter
@Getter
@NoArgsConstructor
public class RefreshToken {
    @Id
    private Long userId;
    private String token;
    private Long timeToLive;

    @Builder
    public RefreshToken(Long userId, String token, Long timeToLive) {
        this.userId = userId;
        this.token = token;
        this.timeToLive = timeToLive;
    }

    public void reIssueToken(String token, Long timeToLive){
        this.token = token;
        this.timeToLive = timeToLive;
    }
}

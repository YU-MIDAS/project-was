package com.example.demo.base.refresh_token;

import com.example.demo.base.exception.CustomException;
import com.example.demo.base.exception.ExceptionCode;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Setter
@Getter
@NoArgsConstructor
public class RefreshToken {
    @Id
    private Long userId;
    private String token;
    private Date timeToLive;

    @Builder
    public RefreshToken(Long userId, String token, Date timeToLive) {
        this.userId = userId;
        this.token = token;
        this.timeToLive = timeToLive;
    }

    public void reIssueToken(String token, Date timeToLive){
        this.token = token;
        this.timeToLive = timeToLive;
    }

    public ResponseCookie generateCookie(){
        return ResponseCookie.from("RefreshToken", token)
                .httpOnly(true)
                .sameSite("None")
                .path("/")
                .maxAge(Duration.ofDays(7))
                .build();
    }
}

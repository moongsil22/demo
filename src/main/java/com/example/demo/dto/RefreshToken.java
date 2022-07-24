package com.example.demo.dto;


import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class RefreshToken {

    private String userId;

    private String accessToken;

    private String accessExpiresIn;

    private String refreshToken;

    @Builder
    public RefreshToken(String userId, String accessToken, String accessExpiresIn, String refreshToken) {
        this.userId = userId;
        this.accessToken = accessToken;
        this.accessExpiresIn = accessExpiresIn;
        this.refreshToken = refreshToken;
    }

    public RefreshToken updateValue(String refreshToken) {
        this.refreshToken = refreshToken;
        return this;
    }
}
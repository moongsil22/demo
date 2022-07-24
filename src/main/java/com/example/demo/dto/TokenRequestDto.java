package com.example.demo.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Getter
@NoArgsConstructor
public class TokenRequestDto {

    @ApiModelProperty(value = "액세스토큰", required = true,example = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ1c2VyMDAxMUBPUkcwMDAxIiwiYXV0aCI6IkNVUyIsImV4cCI6MTY1ODY3MzgwN30.oawP6BTxj19HApJiWSMnaq3pCRwrfaM1jqOWwc6O32yryg0JCVkGWgHBGEa5szQoadMqGkz1NFymaol7D5rY1A")
    @NotNull
    private String accessToken;

    @ApiModelProperty(value = "리프레시토큰", required = true,example = "eyJhbGciOiJIUzUxMiJ9.eyJleHAiOjE2NTkyNzY4MDd9.2K79xjO3rD_gam2pxLPTunzXjF-WPX_MN9n410J18wbqYta4mrwVljwJThmFrCkq1Azj4jmBBRL9XjmIH0sdgA")
    @NotNull
    private String refreshToken;
}

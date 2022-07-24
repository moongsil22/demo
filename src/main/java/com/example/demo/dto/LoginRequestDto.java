package com.example.demo.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.validation.constraints.NotNull;


@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonSerialize
public class LoginRequestDto {


    @ApiModelProperty(value = "사용자 아이디", required = true,example = "user001")
    @NotNull
    String loginUserId;

    @ApiModelProperty(value = "사용자 패스워드", required = true,example = "aa1234")
    @NotNull
    String userPw;

    @ApiModelProperty(value = "로그인한 사용자 기관코드, 앱경로 ORGAPP:기관앱일 경우 필수", example = "ORG0001")
    String loginUserOrgCode;

/*   public void setUserPw(String userPw) {
        PasswordEncoder passwordEncoder = null;

        this.userPw = String.valueOf(passwordEncoder.encode(userPw));
    }*/


    public UsernamePasswordAuthenticationToken toAuthentication() {

        return new UsernamePasswordAuthenticationToken(loginUserId+"@"+loginUserOrgCode, userPw);
    }
}

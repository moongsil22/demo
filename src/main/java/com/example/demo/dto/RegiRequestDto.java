package com.example.demo.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.parameters.P;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.validation.constraints.NotNull;


@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonSerialize
public class RegiRequestDto {

    @ApiModelProperty(value = "사용자 아이디", required = true,example = "user001")
    @NotNull
    String userId;

    @ApiModelProperty(value = "등록할 사용자 패스워드", required = true,example = "1234")
    @NotNull
    String userPw;

    @ApiModelProperty(value = "등록할 사용자 이름", required = true,example = "가나다")
    @NotNull
    String userName;

    @ApiModelProperty(value = "등록할 사용자 식별자번호", required = true,example = "9910012345678")
    @NotNull
    String juminNo;

    @ApiModelProperty(value = "등록할 사용자 권한(OP:기관이용자,CUS:고객)",example = "CUS",allowableValues = "OP,CUS")
    @NotNull
    String userRole;

    @ApiModelProperty(value = "앱접속경로(ORGAPP:기관앱 CUSTAPP:개인앱)",required = true,example = "ORGAPP",allowableValues = "ORGAPP,CUSTAPP")
    @NotNull
    String appType;

    @ApiModelProperty(value = "로그인한 사용자 Id, 앱경로 ORGAPP:기관앱일 경우 필수",example = "id00001")
    String loginUserId;

    @ApiModelProperty(value = "로그인한 사용자 기관코드, 앱경로 ORGAPP:기관앱일 경우 필수", example = "ORG0001")
    String loginUserOrgCode;

    @ApiModelProperty(value = "로그인한 사용자 권한(SUPER:기관 OP:기관이용자 CUS:고객), 앱경로 ORGAPP:기관앱일 경우 필수", example = "SUPER", allowableValues = "SUPER,OP, CUS")
    String loginUserRole;

/*    public void setUserPw(String userPw) {
        PasswordEncoder passwordEncoder = null;
        this.userPw = passwordEncoder.encode(userPw);
    }*/

}

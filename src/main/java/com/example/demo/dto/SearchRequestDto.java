package com.example.demo.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;


@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonSerialize
public class SearchRequestDto {

    @ApiModelProperty(value = "로그인한 사용자 Id",example = "user001", required = true)
    String loginUserId;

    @ApiModelProperty(value = "로그인한 사용자 기관코드, 기업앱의 경우 필수", example = "ORG0001")
    String loginUserOrgCode;


    @ApiModelProperty(value = "로그인한 사용자 권한(SUPER:기관 OP:기관이용자 CUS:고객), 기업앱의 경우 필수", example = "CUS", allowableValues = "SUPER,OP, CUS")
    String loginUserRole;
}

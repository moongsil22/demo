package com.example.demo.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Optional;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SearchResponseDto {


    @JsonProperty(value="userId")
    @ApiModelProperty(value = "사용자 아이디", required = true,example = "user001")
    String user_id;

    @JsonProperty(value="userName")
    @ApiModelProperty(value = "사용자 이름", required = true,example = "가나다")
    String user_name;

    @ApiModelProperty(value = "사용자 권한(SUPER:기관,OP:기관이용자,CUS:고객)",example = "CUS",allowableValues = "SUPER,OP,CUS")
    String userRole;

    @ApiModelProperty(value = "사용자 식별자번호", required = true,example = "9910012345678")
    String juminNo;

    @ApiModelProperty(value = "기관코드", required = true,example = "ORG0001")
    String orgCode;

    @JsonProperty(value="orgName")
    @ApiModelProperty(value = "기관명", required = true,example = "가나다 내과")
    String org_name;

    @JsonProperty(value="testInfo")
    @ApiModelProperty(value = "검사정보", required = true,example = "corona_test_yn:Y")
    String test_yn;


}

package com.example.demo.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginResponseDto {


    @JsonProperty(value="userId")
    @ApiModelProperty(value = "사용자 아이디", required = true,example = "user001")
    String user_id;

    @ApiModelProperty(value = "등록할 사용자 패스워드", required = true,example = "1234")
    @NotNull
    private String user_pw;

    @NotNull
    private String user_name;

    private Userrole userrole;

    private String juminno;

    private String orgcode;

}

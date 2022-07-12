package com.example.demo.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RegiResponseDto {

    @ApiModelProperty(value = "응답코드",example = "0000")
    String rspCode;

    @ApiModelProperty(value = "응답메시지",example = "success")
    String rspMsg;



}

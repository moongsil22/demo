package com.example.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrgRegiUserDto {

    String userId;
    String userName;
    String userRole;
    String juminNo;
    String orgCode;
}

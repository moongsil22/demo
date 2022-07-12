package com.example.demo.dao;

import com.example.demo.dto.*;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface UserMapper {
    List<SearchResponseDto> getUserList(SearchRequestDto searchRequestDto);
    boolean isOrgCode(String orgCode);
    OrgRegiUserDto findOrgRegiUser(RegiRequestDto regiRequestDto);
    UserDto findUser(RegiRequestDto regiRequestDto);
    void saveUser(RegiRequestDto regiRequestDto);
    void saveUserMapping(RegiRequestDto regiRequestDto);
    void saveUserOrg0001(RegiRequestDto regiRequestDto);
    void saveUserOrg0002(RegiRequestDto regiRequestDto);
}

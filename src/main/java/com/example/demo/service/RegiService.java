package com.example.demo.service;

import com.example.demo.dto.*;

import java.util.List;

public interface RegiService {

    List<SearchResponseDto> getUserList(SearchRequestDto searchRequestDto);

    RegiResponseDto save(RegiRequestDto regiRequestDto);

    LoginResponseDto getUserInfo();

    LoginResponseDto getUserInfoById(LoginRequestDto loginRequestDto);

    TokenDto login(LoginRequestDto loginRequestDto);

    TokenDto reissue(TokenRequestDto tokenRequestDto);

}

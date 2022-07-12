package com.example.demo.service;

import com.example.demo.dto.*;

import java.util.List;

public interface RegiService {

    public List<SearchResponseDto> getUserList(SearchRequestDto searchRequestDto);

    public RegiResponseDto save(RegiRequestDto regiRequestDto);

}

package com.example.demo.controller;

import com.example.demo.dto.LoginRequestDto;
import com.example.demo.dto.TokenDto;
import com.example.demo.dto.TokenRequestDto;
import com.example.demo.service.RegiService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class TokenTestController {

    private final RegiService regiService;

    @PostMapping("/login")
    @ApiOperation(value = "로그인", notes = "로그인 시 토큰을 발급합니다.")
    public ResponseEntity<TokenDto> login(@RequestBody LoginRequestDto loginRequestDto) {
        return ResponseEntity.ok(regiService.login(loginRequestDto));
    }

    @PostMapping("/reissue")
    @ApiOperation(value = "토큰 재발급", notes = "리프레시 토큰으로 재발급 합니다")
    public ResponseEntity<TokenDto> reissue(@RequestBody TokenRequestDto tokenRequestDto) {
        return ResponseEntity.ok(regiService.reissue(tokenRequestDto));
    }
}

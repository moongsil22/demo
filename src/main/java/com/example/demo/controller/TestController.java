package com.example.demo.controller;

import com.example.demo.dto.*;
import com.example.demo.service.RegiService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RequiredArgsConstructor
@RestController
public class TestController {

    private final RegiService regiService;

    @PostMapping ( value = "/regi" , consumes = "application/json" , produces = "application/json" )
    @ApiOperation(value = "사용자 등록", notes = "기관코드,사용자타입,식별번호 등을 등록합니다.")
    public RegiResponseDto save(@Valid @RequestBody RegiRequestDto regiRequestDto) {
        return regiService.save(regiRequestDto);
    }

    @PostMapping("/search")
    @ApiOperation(value = "사용자 조회", notes = "사용자 권한(기관,개인)에 따라 조회를 합니다")
    public List<SearchResponseDto> search(@Valid @RequestBody SearchRequestDto searchRequestDto) {
        return regiService.getUserList(searchRequestDto);
    }


    @PostMapping("/login")
    public ResponseEntity<TokenDto> login(@RequestBody LoginRequestDto loginRequestDto) {
        return ResponseEntity.ok(regiService.login(loginRequestDto));
    }

    @PostMapping("/reissue")
    public ResponseEntity<TokenDto> reissue(@RequestBody TokenRequestDto tokenRequestDto) {
        return ResponseEntity.ok(regiService.reissue(tokenRequestDto));
    }
}

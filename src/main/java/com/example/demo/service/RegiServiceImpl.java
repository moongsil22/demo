package com.example.demo.service;

import com.example.demo.dao.UserMapper;
import com.example.demo.dto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RegiServiceImpl implements RegiService{
  private final UserMapper userMapper;

    @Override
    public List<SearchResponseDto> getUserList(SearchRequestDto searchRequestDto)
    {
        return userMapper.getUserList(searchRequestDto);
    }

    @Override
    public RegiResponseDto save(RegiRequestDto regiRequestDto) {

        RegiResponseDto regiResponseDto = new RegiResponseDto();

        regiResponseDto.setRspCode("0000");
        regiResponseDto.setRspMsg("success");

        //기관앱으로 접속했을 경우
        if(regiRequestDto.getAppType().equals("ORGAPP")) {

            //기관코드 존재 확인
            if (!userMapper.isOrgCode(regiRequestDto.getLoginUserOrgCode())) {
                regiResponseDto.setRspCode("9999");
                regiResponseDto.setRspMsg("OrgCode is not exist");
                return regiResponseDto;
            }

            //로그인 사용자 권한확인
            if (regiRequestDto.getLoginUserRole().isEmpty()) {
                regiResponseDto.setRspCode("9999");
                regiResponseDto.setRspMsg("Permission Denied(loginUserRole is empty");
                return regiResponseDto;
            }

            //기관일 경우 기관이용자와 고객 등록가능, 기관이용자일경우 고객 등록가능
            if ((regiRequestDto.getLoginUserRole().equals("SUPER") && regiRequestDto.getUserRole().equals("OP")) ||
                    (regiRequestDto.getLoginUserRole().equals("SUPER") && regiRequestDto.getUserRole().equals("CUS")) ||
                    (regiRequestDto.getLoginUserRole().equals("OP") && regiRequestDto.getUserRole().equals("CUS"))) {

                //기관에서 기등록한 회원인지 확인
                OrgRegiUserDto orgRegiUserDto;
                orgRegiUserDto = userMapper.findOrgRegiUser(regiRequestDto);

                if (orgRegiUserDto != null) {
                    regiResponseDto.setRspCode("9999");
                    regiResponseDto.setRspMsg("already registered");
                    return regiResponseDto;
                } else {
                    //개인앱에서 기등록한 회원인지 확인
                    UserDto userDto;
                    userDto = userMapper.findUser(regiRequestDto);

                    if (userDto != null) {
                        //고객이 개인앱에서 등록한 적이 있는 경우 맵핑테이블에만 적재
                        userMapper.saveUserMapping(regiRequestDto);
                    } else { //신규일 경우 사용자 테이블과 맵핑테이블 같이 적재
                        userMapper.saveUser(regiRequestDto);
                        userMapper.saveUserMapping(regiRequestDto);
                    }

                    //기관사용자테이블에 등록
                    if (regiRequestDto.getLoginUserOrgCode().equals("ORG0001")) {
                        userMapper.saveUserOrg0001(regiRequestDto);
                    } else {
                        userMapper.saveUserOrg0002(regiRequestDto);
                    }
                }
            } else {
                regiResponseDto.setRspCode("9999");
                regiResponseDto.setRspMsg("Permission Denied(loginUserRole is not enough)");
                return regiResponseDto;
            }
        }else{
            //개인앱에서 등록

            //기등록 유무 확인
            UserDto userDto;
            userDto = userMapper.findUser(regiRequestDto);

            if (userDto != null) {
                regiResponseDto.setRspCode("9999");
                regiResponseDto.setRspMsg("already registered");
                return regiResponseDto;
            } else { //신규일 경우 사용자 테이블과 맵핑테이블 같이 적재
                regiRequestDto.setLoginUserId(regiRequestDto.getUserId());

                userMapper.saveUser(regiRequestDto);
            }
        }
        return regiResponseDto;
    }


}

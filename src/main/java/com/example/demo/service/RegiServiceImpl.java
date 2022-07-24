package com.example.demo.service;

import com.example.demo.dao.UserMapper;
import com.example.demo.dto.*;
import com.example.demo.jwt.TokenProvider;
import com.example.demo.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional.*;
@Service
@RequiredArgsConstructor
public class RegiServiceImpl implements RegiService{
    private final UserMapper userMapper;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final PasswordEncoder passwordEncoder;
    private final TokenProvider tokenProvider;

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

                        String pw = regiRequestDto.getUserPw();
                        regiRequestDto.setUserPw(passwordEncoder.encode(pw));
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
            } else { //신규일 경우 사용자 테이블에 적재
                String pw = regiRequestDto.getUserPw();
                regiRequestDto.setUserPw(passwordEncoder.encode(pw));

                regiRequestDto.setLoginUserId(regiRequestDto.getUserId());

                userMapper.saveUser(regiRequestDto);
            }
        }
        return regiResponseDto;
    }

    @Override
    public LoginResponseDto getUserInfo()
    {
        String username = SecurityUtil.getCurrentUserId();
        String[] users = username.split("@");

        String loginUserId = users[0];
        String loginUserOrgCode = users[1];
        return userMapper.getUserInfo(loginUserId,loginUserOrgCode);
    }

    @Override
    public LoginResponseDto getUserInfoById(LoginRequestDto loginRequestDto)
    {
        return userMapper.getUserInfo(loginRequestDto.getLoginUserId(),loginRequestDto.getLoginUserOrgCode());
    }

    @Override
    public TokenDto login(LoginRequestDto loginRequestDto) {


      //  System.out.println("AAA"+ loginRequestDto.getUserPw());

      //  loginRequestDto.setUserPw(loginRequestDto.getUserPw());

      //  System.out.println("BBB"+ loginRequestDto.getUserPw());
        // 1. Login ID/PW 를 기반으로 AuthenticationToken 생성
        UsernamePasswordAuthenticationToken authenticationToken = loginRequestDto.toAuthentication();

        // 2. 실제로 검증 (사용자 비밀번호 체크) 이 이루어지는 부분
        //    authenticate 메서드가 실행이 될 때 CustomUserDetailsService 에서 만들었던 loadUserByUsername 메서드가 실행됨
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

        // 3. 인증 정보를 기반으로 JWT 토큰 생성
        TokenDto tokenDto = tokenProvider.generateTokenDto(authentication);

        // 4. RefreshToken 저장
        RefreshToken refreshToken = RefreshToken.builder()
                .userId(authentication.getName())
                .accessToken(tokenDto.getAccessToken())
                .accessExpiresIn(tokenDto.getAccessTokenExpiresIn().toString())
                .refreshToken(tokenDto.getRefreshToken())
                .build();

        userMapper.refreshTokenSave(refreshToken);

        // 5. 토큰 발급
        return tokenDto;
    }

    @Override
    public TokenDto reissue(TokenRequestDto tokenRequestDto) {
        // 1. Refresh Token 검증
        if (!tokenProvider.validateToken(tokenRequestDto.getRefreshToken())) {
            throw new RuntimeException("Refresh Token 이 유효하지 않습니다.");
        }

        // 2. Access Token 에서 Member ID 가져오기
        Authentication authentication = tokenProvider.getAuthentication(tokenRequestDto.getAccessToken());

        // 3. 저장소에서 Member ID 를 기반으로 Refresh Token 값 가져옴
        RefreshToken refreshToken = userMapper.findTokenById(authentication.getName());

        if(refreshToken == null){
            throw new RuntimeException("로그아웃 된 사용자입니다.\"");
        }
              //  .orElseThrow(() -> new RuntimeException("로그아웃 된 사용자입니다."));

        // 4. Refresh Token 일치하는지 검사
        if (!refreshToken.getRefreshToken().equals(tokenRequestDto.getRefreshToken())) {
            throw new RuntimeException("토큰의 유저 정보가 일치하지 않습니다.");
        }

        // 5. 새로운 토큰 생성
        TokenDto tokenDto = tokenProvider.generateTokenDto(authentication);

        // 6. 저장소 정보 업데이트
        RefreshToken newRefreshToken = RefreshToken.builder()
                .userId(authentication.getName())
                .accessToken(tokenDto.getAccessToken())
                .accessExpiresIn(tokenDto.getAccessTokenExpiresIn().toString())
                .refreshToken(tokenDto.getRefreshToken())
                .build();

        userMapper.refreshTokenSave(newRefreshToken);

        // 토큰 발급
        return tokenDto;
    }


}

package com.example.demo.service;

import com.example.demo.dao.UserMapper;
import com.example.demo.dto.LoginResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
@RequiredArgsConstructor
public class CustomUserDetailService  implements UserDetailsService {

    private final UserMapper userMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        LoginResponseDto loginResponseDto;

        String [] users = username.split("@");

        String loginUserId = users[0];
        String loginUserOrgCode = users[1];
        loginResponseDto = userMapper.getUserInfo(loginUserId,loginUserOrgCode);

        if(loginResponseDto == null){
            throw new UsernameNotFoundException(username + " -> 데이터베이스에서 찾을 수 없습니다.");
        }else{
            return createUserDetails(loginResponseDto);
        }
    }


    // DB 에 User 값이 존재한다면 UserDetails 객체로 만들어서 리턴
    private UserDetails createUserDetails(LoginResponseDto loginResponseDto) {
        GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(loginResponseDto.getUserrole().toString());

        return new User(
                loginResponseDto.getUser_id() +"@"+loginResponseDto.getOrgcode(),
                loginResponseDto.getUser_pw(),
                Collections.singleton(grantedAuthority)
        );
    }


}

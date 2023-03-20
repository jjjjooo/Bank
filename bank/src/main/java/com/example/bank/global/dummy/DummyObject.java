package com.example.bank.global.dummy;

import com.example.bank.user.domain.User;
import com.example.bank.user.domain.UserRole;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.time.LocalDateTime;

public class DummyObject {

    protected User newUser(String username, String fullName){
        BCryptPasswordEncoder passwordEncoder  = new BCryptPasswordEncoder();
        String encPassword = passwordEncoder.encode("1234");
        return User.builder()
                .username(username)
                .password(encPassword)
                .email("aa@naver.com")
                .fullName(fullName)
                .userRole(UserRole.CUSTOM)
                .build();
    }

    protected User mockUser(Long id, String username, String fullName){
        BCryptPasswordEncoder passwordEncoder  = new BCryptPasswordEncoder();
        String encPassword = passwordEncoder.encode("1234");
        return User.builder()
                .id(id)
                .username(username)
                .password(encPassword)
                .email("aa@naver.com")
                .fullName(fullName)
                .userRole(UserRole.CUSTOM)
                .createAt(LocalDateTime.now())
                .updateAt(LocalDateTime.now())
                .build();
    }
}

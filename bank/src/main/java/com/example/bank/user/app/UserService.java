package com.example.bank.user.app;

import com.example.bank.global.exception.CustomApiException;
import com.example.bank.user.domain.User;
import com.example.bank.user.domain.UserRepository;
import com.example.bank.user.dto.UserReqDto;
import com.example.bank.user.dto.UserResDto;
import lombok.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserService {
    private final Logger log = LoggerFactory.getLogger(getClass());
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Transactional
    public UserResDto.JoinResDto 회원가입(UserReqDto.JoinReqDto joinReqDto){
        //1. 동일 유저 검사
        Optional<User> userOp = userRepository.findByUsername(joinReqDto.getUsername());
        if(userOp.isPresent()){
            throw new CustomApiException("동일한 유저가 존재합니다.");
        }

        //2. 패스워드 인코딩
        User userPs = userRepository.save(joinReqDto.toEntity(bCryptPasswordEncoder));

        //3. dto 응답
        return new UserResDto.JoinResDto(userPs);
    }
}

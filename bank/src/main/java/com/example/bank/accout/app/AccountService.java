package com.example.bank.accout.app;

import com.example.bank.accout.domain.Account;
import com.example.bank.accout.domain.AccountRepository;
import com.example.bank.accout.dto.AccountReqDto;
import com.example.bank.accout.dto.AccountResDto;
import com.example.bank.global.exception.CustomApiException;
import com.example.bank.user.domain.User;
import com.example.bank.user.domain.UserRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.Optional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class AccountService {
    private final UserRepository userRepository;
    private final AccountRepository accountRepository;

    @Transactional
    public AccountResDto.AccountSaveResDto 꼐좌등록(AccountReqDto.AccountSaveReqDto accountSaveReqDto, Long userId){
        //User 가 로그인도어 있냐?
        User userPs = userRepository.findById(userId).orElseThrow(()-> new CustomApiException("유저를 찾을 수 없습니다."));

        //등록할 계좌 중복여부
        Optional<Account> accountOp = accountRepository.findByNumber(accountSaveReqDto.getNumber());
        if(accountOp.isPresent()){
            throw new CustomApiException("해당 계좌가 이미 존재합니다.");
        }

        //계좌 등록
        Account accountPs = accountRepository.save(accountSaveReqDto.toEntity(userPs));

        //DTO 응답
        return new AccountResDto.AccountSaveResDto(accountPs);
    }
}

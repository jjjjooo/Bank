package com.example.bank.accout.presentation;

import com.example.bank.accout.app.AccountService;
import com.example.bank.accout.dto.AccountReqDto;
import com.example.bank.accout.dto.AccountResDto;
import com.example.bank.global.dto.ResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RequiredArgsConstructor
@RequestMapping("/api")
@RestController
public class AccountController {
    private final AccountService accountService;

    @PostMapping("/s/account")
    public ResponseEntity<?> saveAccount(@RequestBody @Valid AccountReqDto.AccountSaveReqDto accountSaveReqDto,
                                         BindingResult bindingResult,
                                         @AuthenticationPrincipal Long user){
        AccountResDto.AccountSaveResDto 꼐좌등록 = accountService.꼐좌등록(accountSaveReqDto, user);
        return new ResponseEntity<>(new ResponseDto<>(1,"계좌등록 성공", accountSaveReqDto), HttpStatus.CREATED);
    }

}

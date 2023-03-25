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
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/s/account/login-user")
    public ResponseEntity<?> findUserAccount( @AuthenticationPrincipal Long user){
        AccountResDto.AccountListResDto accountListResDto = accountService.계좌목록보기_유저별(user);
        return new ResponseEntity<>(new ResponseDto<>(1,"계좌등록 성공", accountListResDto), HttpStatus.CREATED);
    }

    @DeleteMapping("/s/account/{number}")
    public ResponseEntity<?> deleteAccount(@PathVariable Long number, @AuthenticationPrincipal Long userId){
        accountService.계좌삭제(number, userId);
        return new ResponseEntity<>(new ResponseDto<>(1,"계좌 삭제 완료",null),HttpStatus.OK);
    }

    @PostMapping("/s/account/deposit")
    public ResponseEntity<?> depositAccount(@RequestBody @Valid AccountReqDto.AccountDepositReqDto accountDepositReqDto,
                                            BindingResult bindingResult){
        AccountResDto.AccountDepositResDto accountDepositResDto = accountService.계좌입금(accountDepositReqDto);
        return new ResponseEntity<>(new ResponseDto<>(1,"계좌 삭제 완료",accountDepositResDto),HttpStatus.OK);
    }
}

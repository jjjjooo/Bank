package com.example.bank.accout.app;

import com.example.bank.accout.domain.Account;
import com.example.bank.accout.domain.AccountRepository;
import com.example.bank.accout.dto.AccountReqDto;
import com.example.bank.accout.dto.AccountResDto;
import com.example.bank.global.exception.CustomApiException;
import com.example.bank.transaction.domain.Action;
import com.example.bank.transaction.domain.TransactionRepository;
import com.example.bank.transaction.domain.TransactionType;
import com.example.bank.user.domain.User;
import com.example.bank.user.domain.UserRepository;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import lombok.Setter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import javax.validation.constraints.Digits;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class AccountService {
    private final UserRepository userRepository;
    private final AccountRepository accountRepository;
    private final TransactionRepository transactionRepository;

    @Transactional
    public AccountResDto.AccountWithdrawDepositResDto 계좌출금(AccountWithdrawReqDto accountWithdrawReqDto, Long userId){
        if(accountWithdrawReqDto.getAmount() <= 0L){
            throw new CustomApiException("0원 이하의 금액을 입금할 수 없습니다.");
        }
        // 출금 계좌 확인
        Account withdrawAccountPs = accountRepository.findByNumber(accountWithdrawReqDto.getNumber())
                .orElseThrow(() -> new CustomApiException("계좌를 찾을 수 없습니다."));

        // 출금 소유자 확인
        withdrawAccountPs.checkOwner(userId);

        // 출금계좌 비밀번호 확인
        withdrawAccountPs.checkPassword(accountWithdrawReqDto.getPassword());
        // 출금계좌 잔액확인
        withdrawAccountPs.checkBalance(accountWithdrawReqDto.getAmount());
        //출금하기
        withdrawAccountPs.withdraw(accountWithdrawReqDto.getAmount());
        //거래내역 남기기

        Action action = Action.builder()
                .depositAccount(withdrawAccountPs)
                .withdrawAccount(null)
                .depositAccountBalance(withdrawAccountPs.getBalance())
                .withdrawAccountBalance(null)
                .amount(accountWithdrawReqDto.getAmount())
                .transactionType(TransactionType.WITHDRAW)
                .sender(accountWithdrawReqDto.getNumber()+"")
                .receiver("ATM")
                .createAt(LocalDateTime.now())
                .updateAt(LocalDateTime.now())
                .build();
        Action actionPs = transactionRepository.save(action);
        //응답
        return new AccountResDto.AccountWithdrawDepositResDto(withdrawAccountPs, actionPs);
    }

    @Getter
    @Setter
    public static class AccountWithdrawReqDto {
        @NotNull
        @Digits(integer = 4, fraction = 4)
        private Long number;
        @NotNull
        @Digits(integer = 4, fraction = 4)
        private Long password;
        @NotNull
        private Long amount;
        @NotEmpty
        @Pattern(regexp = "^(WITHDRAW)$")
        private String gubun;
    }


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

    @Transactional(readOnly = true)
    public AccountResDto.AccountListResDto 계좌목록보기_유저별(long userId){
        User userPs = userRepository.findById(userId).orElseThrow(()-> new CustomApiException("유저를 찾을 수 없습니다."));
        List<Account> accountPs = accountRepository.findByUser_id(userId);
        return new AccountResDto.AccountListResDto(userPs, accountPs);
    }

    @Transactional
    public void 계좌삭제(Long accountNumber, Long userId){
        //1. 계좌 확인
        Account accountPs = accountRepository.findByNumber(accountNumber).orElseThrow(
                () -> new CustomApiException("계좌를 찾을 수 없습니다."));
        //2. 계좌 소유자 확인
        accountPs.checkOwner(userId);

        //3. 계좌삭제
        accountRepository.deleteById(accountPs.getId());
    }

    @Transactional
    public AccountResDto.AccountDepositResDto 계좌입금(AccountReqDto.AccountDepositReqDto accountDepositReqDto){
        //0원 체크
        if(accountDepositReqDto.getAmount() <= 0L){
            throw new CustomApiException("0원 이하의 금액을 입금할 수 없습니다.");
        }
        //입금계좌 확인
        Account depositAccountPs = accountRepository.findByNumber(accountDepositReqDto.getNumber())
                .orElseThrow(() -> new CustomApiException("계좌를 찾을 수 없습니다."));

        //입금(더티체킹)
        depositAccountPs.deposit(accountDepositReqDto.getAmount());
        Action atm = Action.builder().
                depositAccount(depositAccountPs)
                .withdrawAccount(null)
                .depositAccountBalance(depositAccountPs.getBalance())
                .withdrawAccountBalance(null)
                .amount(accountDepositReqDto.getAmount())
                .transactionType(TransactionType.DEPOSIT)
                .sender(null)
                .sender("ATM")
                .receiver(accountDepositReqDto.getNumber() + "")
                .tel(accountDepositReqDto.getTel())
                .createAt(LocalDateTime.now())
                .updateAt(LocalDateTime.now())
                .build();
        //거래내역 남기기
        transactionRepository.save(atm);

        return new AccountResDto.AccountDepositResDto(depositAccountPs, atm);
    }
}

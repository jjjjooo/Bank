package com.example.bank.accout.app;


import com.example.bank.accout.domain.Account;
import com.example.bank.accout.domain.AccountRepository;
import com.example.bank.accout.dto.AccountReqDto;
import com.example.bank.accout.dto.AccountReqDto.*;
import com.example.bank.accout.dto.AccountResDto;
import com.example.bank.global.dummy.DummyObject;
import com.example.bank.global.exception.CustomApiException;
import com.example.bank.transaction.domain.Action;
import com.example.bank.transaction.domain.TransactionRepository;
import com.example.bank.transaction.domain.TransactionType;
import com.example.bank.user.domain.User;
import com.example.bank.user.domain.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AccountServiceTest extends DummyObject {

    @InjectMocks
    private AccountService accountService;
    @Mock
    private UserRepository userRepository;
    @Spy
    private ObjectMapper om;
    @Mock
    private AccountRepository accountRepository;
    @Mock
    private TransactionRepository transactionRepository;

    @Test
    void 계좌등록_성공_테스트() throws Exception {
        //given
        Long userId = 1L;
       AccountSaveReqDto accountSaveReqDto = new AccountSaveReqDto();
       accountSaveReqDto.setNumber(1111L);
       accountSaveReqDto.setPassword(1234L);

       //stub1
        User user = mockUser(1L, "ssar", "쌀");
        when(userRepository.findById(any())).thenReturn(Optional.of(user));

        //stub2
        when(accountRepository.findByNumber(any())).thenReturn(Optional.empty());

        //stub3
        Account account = mockAccount(1L, 1111L, 1000L, user);
        when(accountRepository.save(any())).thenReturn(account);

        //when
        AccountResDto.AccountSaveResDto 꼐좌등록 = accountService.꼐좌등록(accountSaveReqDto, userId);
        String responseBody = om.writeValueAsString(accountSaveReqDto);
        System.out.println(responseBody);

        //then
        assertThat(accountSaveReqDto.getNumber()).isEqualTo(1111L);
    }

    @Test
    void 계좌삭제_실패() throws Exception{
        //given
        Long number = 1111L;
        Long userId = 2L;

        //stub
        User user = mockUser(1L,"user", "유저1");
        Account userAccount = mockAccount(1L, 1111L, 1000L, user);
        when(accountRepository.findByNumber(any())).thenReturn(Optional.of(userAccount));

        //when
        assertThrows(CustomApiException.class, ()-> accountService.계좌삭제(number, userId));
    }
    @Test
    void 계좌입금_test() throws Exception{
        //given
        AccountReqDto.AccountDepositReqDto accountDepositReqDto
                = new AccountDepositReqDto(1111L, 100L, TransactionType.DEPOSIT.getValue(), "01000000000");

        //stub1
        User user1 = mockUser(1L, "user1", "유저1");
        Account user1Account = mockAccount(1L, 1111L, 1000L, user1);
        when(accountRepository.findByNumber(any())).thenReturn(Optional.of(user1Account));

        //stub2
        Account user1Account2 = mockAccount(1L, 1111L, 1000L, user1);
        Action action = mockDepostionAction(1L, user1Account2);
        when(transactionRepository.save(any())).thenReturn(action);

        //when
        AccountResDto.AccountDepositResDto accountDepositResDto = accountService.계좌입금(accountDepositReqDto);
        System.out.println(accountDepositResDto.getTransactionDto().getDepositAccountBalance());
        System.out.println(user1Account.getBalance());
        //then

        assertThat(user1Account.getBalance()).isEqualTo(1100L);
        assertThat(accountDepositResDto.getTransactionDto().getDepositAccountBalance()).isEqualTo(1100L);
    }

    @Test
    public void 계좌출금_test() throws Exception{
        //given
        AccountService.AccountWithdrawReqDto accountWithdrawReqDto =
                new AccountService.AccountWithdrawReqDto();
        accountWithdrawReqDto.setAmount(100L);
        accountWithdrawReqDto.setGubun("WITHDRAW");
        accountWithdrawReqDto.setNumber(1111L);
        accountWithdrawReqDto.setPassword(1234L);

        User user1 = mockUser(1L,"user1", "유저1");
        Account account1 = mockAccount(1L, 1111L, 1000L, user1);

        //when
        //0원 체크
        if(accountWithdrawReqDto.getAmount() <= 0L){
            throw new CustomApiException("0원 이하의 금액을 입금할 수 없습니다.");
        }

        account1.checkOwner(2L);
        account1.checkPassword(1234L);

        account1.withdraw(10000L);
    }
}
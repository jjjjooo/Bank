package com.example.bank.global.dummy;

import com.example.bank.accout.domain.Account;
import com.example.bank.transaction.domain.Action;
import com.example.bank.transaction.domain.TransactionType;
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

    protected Account mockAccount(Long id, Long number, Long balance, User user){
        return Account.builder()
                .id(id)
                .balance(balance)
                .number(number)
                .password(1234l)
                .balance(1000L)
                .user(user)
                .createAt(LocalDateTime.now())
                .updateAt(LocalDateTime.now())
                .build();
    }
    protected Account newAccount(Long number,  User user){
        return Account.builder()
                .number(number)
                .password(1111L)
                .balance(1000L)
                .user(user)
                .createAt(LocalDateTime.now())
                .updateAt(LocalDateTime.now())
                .build();
    }

    protected Action mockDepostionAction(Long id, Account account){
        return Action.builder()
                .id(id)
                .depositAccount(account)
                .withdrawAccount(null)
                .depositAccountBalance(account.getBalance())
                .withdrawAccountBalance(null)
                .amount(100L)
                .transactionType(TransactionType.DEPOSIT)
                .sender(null)
                .sender("ATM")
                .receiver(account.getNumber() + "")
                .tel("01000001111")
                .createAt(LocalDateTime.now())
                .updateAt(LocalDateTime.now())
                .build();
    }

}

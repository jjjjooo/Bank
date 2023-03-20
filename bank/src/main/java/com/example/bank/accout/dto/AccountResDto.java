package com.example.bank.accout.dto;

import com.example.bank.accout.domain.Account;
import lombok.Getter;
import lombok.Setter;

public class AccountResDto {
    @Setter
    @Getter
    public static class AccountSaveResDto {
        private Long id;
        private Long number;
        private Long balance;

        public AccountSaveResDto(Account account) {
            this.id = account.getId();
            this.number = account.getNumber();
            this.balance = account.getBalance();
        }
    }
}

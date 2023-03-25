package com.example.bank.accout.dto;

import com.example.bank.accout.domain.Account;
import com.example.bank.global.util.CustomDateUtil;
import com.example.bank.transaction.domain.Action;
import com.example.bank.user.domain.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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

    @Getter
    @Setter
    public static class AccountListResDto{
        private String fullName;

        private List<AccountDto> accounts = new ArrayList<>();

        public AccountListResDto(User user, List<Account> accounts) {
            this.fullName = user.getUsername();
            this.accounts = accounts.stream().map(AccountDto::new).collect(Collectors.toList());
        }

        @Getter
        @Setter
        private class AccountDto {
            private Long id;
            private Long number;
            private Long balance;

            public AccountDto(Account account) {
                this.id = account.getId();
                this.number = account.getNumber();
                this.balance = account.getBalance();
            }
        }
    }

    @Getter
    @Setter
    @ToString
    public static class AccountDepositResDto{
        private Long id;
        private Long number;
        private TransactionDto transactionDto;

        public AccountDepositResDto(Account account, Action action) {
            this.id = account.getId();
            this.number = account.getNumber();
            this.transactionDto = new TransactionDto(action);
        }

        @Getter
        @Setter
        public class TransactionDto{
            private Long id;
            private String gubun;
            private String sender;
            private String receiver;
            private Long amount;
            @JsonIgnore // 테스트용도로 확인만
            private Long depositAccountBalance;
            private String tel;
            private String createdAt;

            public TransactionDto(Action action) {
                this.id = action.getId();
                this.gubun = action.getTransactionType().getValue();
                this.sender = action.getSender();
                this.receiver = action.getReceiver();
                this.amount = action.getAmount();
                this.depositAccountBalance = action.getDepositAccountBalance();
                this.tel = action.getTel();
                this.createdAt = CustomDateUtil.toStringFormat(action.getCreateAt());
            }
        }
    }

    @Getter
    @Setter
    @ToString
    public static class AccountWithdrawDepositResDto{
        private Long id;
        private Long number;
        private Long balance;
        private TransactionDto transactionDto;

        public AccountWithdrawDepositResDto(Account account, Action action) {
            this.id = account.getId();
            this.number = account.getNumber();
            this.balance = account.getBalance();
            this.transactionDto = new TransactionDto(action);
        }

        @Getter
        @Setter
        public class TransactionDto{
            private Long id;
            private String gubun;
            private String sender;
            private String receiver;
            private Long amount;
            private String createdAt;

            public TransactionDto(Action action) {
                this.id = action.getId();
                this.gubun = action.getTransactionType().getValue();
                this.sender = action.getSender();
                this.receiver = action.getReceiver();
                this.amount = action.getAmount();
                this.createdAt = CustomDateUtil.toStringFormat(action.getCreateAt());
            }
        }
    }
}

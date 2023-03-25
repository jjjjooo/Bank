package com.example.bank.accout.dto;

import com.example.bank.accout.domain.Account;
import com.example.bank.user.domain.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

public class AccountReqDto {
    @Setter
    @Getter
    public static class AccountSaveReqDto{
        @NotNull
        @Digits(integer = 4, fraction = 4)
        private Long number;
        @NotNull
        @Digits(integer = 4, fraction = 4)
        private Long password;
        public Account toEntity(User user){
            return Account.builder()
                    .number(number)
                    .password(password)
                    .balance(1000L)
                    .user(user)
                    .build();
        }
    }

    @Getter
    @Setter
    @NoArgsConstructor
    public static class AccountDepositReqDto{
        @NotNull
        @Digits(integer = 4, fraction = 4)
        private Long number;
        @NotNull
        private Long amount;
        @NotEmpty
        @Pattern(regexp ="^(DEPOSIT|TRANSFER)$")
        private String gubun;
        @NotEmpty
        @Pattern(regexp = "^[0-1]{3}[0-9]{4}[0-9]{4}")
        private String tel;

        public AccountDepositReqDto(Long number, Long amount, String gubun, String tel) {
            this.number = number;
            this.amount = amount;
            this.gubun = gubun;
            this.tel = tel;
        }
    }
}

package com.example.bank.global.dummy;

import com.example.bank.accout.domain.Account;
import com.example.bank.accout.domain.AccountRepository;
import com.example.bank.user.domain.User;
import com.example.bank.user.domain.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
public class DummyDevInit extends DummyObject {
    @Profile("dev")
    @Bean
    CommandLineRunner init(UserRepository userRepository, AccountRepository accountRepository){
        return (args -> {
            User love = userRepository.save(newUser("love","럽럽"));
            User ssar = userRepository.save(newUser("ssar","쌀"));
            User cos = userRepository.save(newUser("cos","코스"));
            Account ssarAccount = accountRepository.save(newAccount(1111L, ssar));
            Account cosAccount = accountRepository.save(newAccount(1112L, cos));
        });
    }
}

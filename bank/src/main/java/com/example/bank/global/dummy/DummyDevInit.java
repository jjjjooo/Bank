package com.example.bank.global.dummy;

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
    CommandLineRunner init(UserRepository userRepository){
        return (args -> {
            User love = userRepository.save(newUser("love","럽럽"));
        });
    }
}

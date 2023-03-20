package com.example.bank.user.dto;

import com.example.bank.user.domain.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public class UserResDto {
    @Getter
    @Setter
    @NoArgsConstructor
    public static class JoinResDto{
        private Long id;
        private String username;
        private String fullName;

        public JoinResDto(User user) {
            this.id = user.getId();
            this.username = user.getUsername();
            this.fullName = getFullName();
        }
    }
}

package com.example.bank.user.dto;

import com.example.bank.user.domain.User;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class UserReqDto {
    @Setter
    @Getter
    @ToString
    public static class JoinReqDto{
        @Pattern(regexp = "^[a-zA-Z0-9]{2,20}$", message = "영문/숫자 포함 20자 이하로 해주세요")
        @NotEmpty
        private String username;

        @Size(min = 4, max =20)
        @NotEmpty
        private String password;
        @Pattern(regexp = "^[a-zA-Z0-9]{4,6}@[a-zA-Z0-9]{2,6}\\.[a-zA-Z]{2,3}$", message = "영문/숫자 포함 20자 이하로 해주세요")
        @NotEmpty
        private String email;
        @Pattern(regexp = "^[a-zA-Z가-힣]{1,20}$", message = "한글/영문 1~10자 이하로 작성해주세요")
        @NotEmpty
        private String fullName;

        public User toEntity(BCryptPasswordEncoder passwordEncoder){
            return User.builder()
                    .username(username)
                    .password(passwordEncoder.encode(password))
                    .email(email)
                    .fullName(fullName)
                    .build();
        }
    }
}

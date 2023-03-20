package com.example.bank.user.app;

import com.example.bank.global.dummy.DummyObject;
import com.example.bank.user.domain.User;
import com.example.bank.user.domain.UserRepository;
import com.example.bank.user.domain.UserRole;
import com.example.bank.user.dto.UserReqDto;
import com.example.bank.user.dto.UserResDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTest extends DummyObject {
    @InjectMocks
    private UserService userService;
    @Mock
    private UserRepository userRepository;
    @Spy
    private BCryptPasswordEncoder passwordEncoder;

    @Test
    public void 회원가입_test() throws Exception{
        // given
        UserReqDto.JoinReqDto joinReqDto = new UserReqDto.JoinReqDto();
        joinReqDto.setUsername("ssar");
        joinReqDto.setPassword("1234");
        joinReqDto.setEmail("aa@naver.com");
        joinReqDto.setFullName("쌀쌀");

        //stub1
        when(userRepository.findByUsername(any()) ).thenReturn(Optional.empty());
        //stub2
        User ssar = mockUser(1L,"ssar", "쌀");
        when(userRepository.save(any())).thenReturn(ssar);

        //when
        UserResDto.JoinResDto joinResDto= userService.회원가입(joinReqDto);
        System.out.println("테스트 : " + joinResDto);

        //then
        assertThat(joinResDto.getId()).isEqualTo(1L);
        assertThat(joinResDto.getUsername()).isEqualTo("ssar");

    }
}
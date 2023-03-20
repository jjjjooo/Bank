package com.example.bank.user.presentation;

import com.example.bank.global.dummy.DummyObject;
import com.example.bank.user.domain.User;
import com.example.bank.user.domain.UserRepository;
import com.example.bank.user.dto.UserReqDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
class UserControllerTest extends DummyObject {
    @Autowired
    private MockMvc mvc;
    @Autowired
    private ObjectMapper om;
    @Autowired
    private UserRepository repository;
    @BeforeEach
    public void setUp(){
        dataSetting();
    }

    @Test
    public void join_success_test() throws Exception{
        //given
        UserReqDto.JoinReqDto joinReqDto = new UserReqDto.JoinReqDto();
        joinReqDto.setUsername("love1");
        joinReqDto.setPassword("1234");
        joinReqDto.setEmail("love@naver.com");
        joinReqDto.setFullName("러브1");

        String requestBody = om.writeValueAsString(joinReqDto);

        //when
        ResultActions resultActions = mvc.perform(post("/api/join")
                .content(requestBody).contentType(MediaType.APPLICATION_JSON));
        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        //System.out.println(responseBody);

        //then
        resultActions.andExpect(status().isCreated());
    }

    @Test
    public void join_fail_test() throws Exception{
        //given
        UserReqDto.JoinReqDto joinReqDto = new UserReqDto.JoinReqDto();
        joinReqDto.setUsername("ssar");
        joinReqDto.setPassword("1234");
        joinReqDto.setEmail("love@naver.com");
        joinReqDto.setFullName("쌀");

        String requestBody = om.writeValueAsString(joinReqDto);

        //when
        ResultActions resultActions = mvc.perform(post("/api/join")
                .content(requestBody).contentType(MediaType.APPLICATION_JSON));
        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        //System.out.println(responseBody);

        //then
        resultActions.andExpect(status().isBadRequest());
    }


    private void dataSetting() {
        repository.save(newUser("ssar", "쌀"));
    }
}
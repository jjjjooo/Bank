package com.example.bank.accout.presentation;

import com.example.bank.accout.app.Accout;
import com.example.bank.accout.domain.Account;
import com.example.bank.accout.domain.AccountRepository;
import com.example.bank.accout.dto.AccountReqDto;
import com.example.bank.global.dummy.DummyObject;
import com.example.bank.global.exception.CustomApiException;
import com.example.bank.transaction.domain.TransactionType;
import com.example.bank.user.domain.User;
import com.example.bank.user.domain.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.TestExecutionEvent;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Sql("classpath:db/teardown.sql")
@ActiveProfiles("test")
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
class AccountControllerTest extends DummyObject {

    @Autowired
    private MockMvc mvc;
    @Autowired
    private ObjectMapper om;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private EntityManager em;

    @BeforeEach
    void setUp() {
        User ssar = userRepository.save(newUser("ssar","쌀"));
        User cos = userRepository.save(newUser("cos","코스"));
        Account ssarAccount = accountRepository.save(newAccount(1111L, ssar));
        Account cosAccount = accountRepository.save(newAccount(1112L, cos));
        em.clear();
    }

    //@WithUserDetails(value = "ssar", setupBefore = TestExecutionEvent.TEST_EXECUTION)
    @Test
    void saveAccount_test() throws Exception {
        //given
        AccountReqDto.AccountSaveReqDto accountSaveReqDto = new AccountReqDto.AccountSaveReqDto();
        accountSaveReqDto.setPassword(1234L);
        accountSaveReqDto.setNumber(9999L);
        String requestBody = om.writeValueAsString(accountSaveReqDto);
        System.out.println(requestBody);

        //when
        ResultActions resultActions = mvc.perform(post("/api/s/account").content(requestBody).contentType(MediaType.APPLICATION_JSON));
        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println(responseBody);
        //then
        resultActions.andExpect(status().isCreated());
    }

    @Test
    void deleteAccount_test() throws Exception{
        //given
        Long number = 1112L;

        //when
        ResultActions resultActions = mvc.perform(delete("/api/s/account/" + number));
        String requestBody = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println(requestBody);

        //then
        assertThrows(CustomApiException.class, () -> accountRepository.findByNumber(number).orElseThrow(
                () -> new CustomApiException("게좌를 찾을 수 없습니다.")
        ));
    }

    @Test
    public void depositAccount_test() throws Exception{
        //given
        AccountReqDto.AccountDepositReqDto accountDepositReqDto
                = new AccountReqDto.AccountDepositReqDto(1111L, 100L, "DEPOSIT", "01000000000");
        String responseBody = om.writeValueAsString(accountDepositReqDto);
        System.out.println("테스트 : " + responseBody);
        //when
        ResultActions resultActions = mvc.perform(post("/api/s/account/deposit").content(responseBody)
                .contentType(MediaType.APPLICATION_JSON));
        String requestBody = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println(requestBody);

        //then
        resultActions.andExpect(status().isOk());
    }
}
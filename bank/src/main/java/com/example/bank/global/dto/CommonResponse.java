package com.example.bank.global.dto;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CommonResponse {
    private static final Logger log = LoggerFactory.getLogger(CommonResponse.class);

    public static void unAuthentication(HttpServletResponse response, String msg){
        try {
            ObjectMapper mapper = new ObjectMapper();
            ResponseDto<?> responseDto = new ResponseDto<>(-1, "권한없음", null);
            String responseBody = mapper.writeValueAsString(responseDto);
            response.setContentType("application/json; charset=utf-8");
            response.setStatus(401);
            response.getWriter().println(responseBody);
        } catch (Exception e) {
            log.error("파싱 에러");
        }
    }
}


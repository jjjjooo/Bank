package com.example.bank;

import org.junit.jupiter.api.Test;

import java.util.regex.Pattern;

public class RegexTest {
    @Test
    public void 한글만된다() throws Exception{
        String value = "가나";
        boolean result = Pattern.matches("^[가-힣]*$", value);
        System.out.println("테스트 : " + result);
    }

    @Test
    public void 영어만된다() throws Exception{
        String value = "abc";
        boolean result = Pattern.matches("^[A-Za-z]*$", value);
        System.out.println("테스트 : " + result);
    }

    @Test
    public void 한글은안된다() throws Exception{
        String value = "!s";
        boolean result = Pattern.matches("^[^ㄱ-ㅎ가-힣]*$", value);
        System.out.println("테스트 : " + result);
    }
    @Test
    public void 영어는안된다() throws Exception{
        String value = " ";
        boolean result = Pattern.matches("^[^A-Za-z]*$", value);
        System.out.println("테스트 : " + result);
    }
    @Test
    public void 영어와숫자만된다() throws Exception{
        String value = "sssaa11!";
        boolean result = Pattern.matches("^[A-Za-z0-9]*$", value);
        System.out.println("테스트 : " + result);
    }
    @Test
    public void 영어만되고길이는2_4이다() throws Exception{
        String value = "aa";
        boolean result = Pattern.matches("^[A-Za-z]{2,4}$", value);
        System.out.println("테스트 : " + result);
    }

    @Test
    public void user_username_test(){
        String value = "s2ar";
        boolean result = Pattern.matches("^[a-zA-Z0-9]{2,4}$", value);
        System.out.println("테스트 : " + result);
    }

    @Test
    public void user_fullName_test(){
        String value = "sar앙";
        boolean result = Pattern.matches("^[a-zA-Z가-힣]{1,20}$", value);
        System.out.println("테스트 : " + result);
    }

    @Test
    public void user_tempEmail_test(){
        String value = "sara@naver.com";
        boolean result = Pattern.matches("^[a-zA-Z0-9]{4,6}@[a-zA-Z0-9]{2,6}\\.[a-zA-Z]{2,3}$", value);
        System.out.println("테스트 : " + result);
    }
}

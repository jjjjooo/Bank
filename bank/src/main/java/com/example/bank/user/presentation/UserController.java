package com.example.bank.user.presentation;

import com.example.bank.global.dto.ResponseDto;
import com.example.bank.user.app.UserService;
import com.example.bank.user.dto.UserReqDto.JoinReqDto;
import com.example.bank.user.dto.UserResDto.JoinResDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@RequestMapping("/api")
@RestController
public class UserController {
    private final UserService userService;

    @PostMapping("/join")
    public ResponseEntity<?> join(@RequestBody @Valid JoinReqDto joinReqDto, BindingResult bindingResult){


        JoinResDto joinResDto = userService.회원가입(joinReqDto);
        return new ResponseEntity<>(new ResponseDto<>(1, "ok", joinResDto), HttpStatus.CREATED);
    }
}

package com.example.bank.global.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class ResponseDto<T> {
    public final Integer code; // 1 -1
    private final String msg;
    private final T data;

}

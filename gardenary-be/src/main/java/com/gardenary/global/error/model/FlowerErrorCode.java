package com.gardenary.global.error.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum FlowerErrorCode implements ErrorCode {
    FLOWER_NOT_FOUND(5000, HttpStatus.NOT_FOUND, "Flower is Not Found");

    private final int code;
    private final HttpStatus httpStatus;
    private final String message;
}

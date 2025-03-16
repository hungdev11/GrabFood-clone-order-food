package com.api.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {
    SOME_THING_WENT_WRONG(9999, "Not OK", HttpStatus.BAD_REQUEST),
    RESOURCE_NOT_FOUND(404, "Not Found", HttpStatus.NOT_FOUND),
    //ACCOUNT
    ACCOUNT_USERNAME_DUPLICATED(0000, "Username of an account is duplicated", HttpStatus.BAD_REQUEST),
    ACCOUNT_USERNAME_NOT_EXISTED(0001, "Username of an account not found", HttpStatus.BAD_REQUEST),
    ACCOUNT_PASSWORD_NOT_MATCH(0002, "Wrong password", HttpStatus.BAD_REQUEST),
    //FOOD - FOOD TYPE
    FOODTYPE_NAME_EXISTED(0050, "Type of food is already existed", HttpStatus.BAD_REQUEST),
    FOODTYPE_NAME_NOT_EXISTED(0051, "Type of food not found", HttpStatus.BAD_REQUEST),

    //RESTAURANT
    RESTAURANT_NOT_FOUND(0100, "Restaurant not found", HttpStatus.BAD_REQUEST),

    ;
    private final int code;
    private final String message;
    private final HttpStatus statusCode;

    ErrorCode(int code, String message, HttpStatus statusCode) {
        this.code = code;
        this.message = message;
        this.statusCode = statusCode;
    }
}

package com.example.demo.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

@Getter
public enum ErrorCode {
    UNCATEGORIZED_EXCEPTION(9999, "Uncategorized error !!!", HttpStatus.INTERNAL_SERVER_ERROR),
    INVALID_KEY(1001, "Invalid message key !!!", HttpStatus.BAD_REQUEST),
    USER_EXISTED(1002, "User existed !!!", HttpStatus.BAD_REQUEST),
    USERNAME_INVALID(1003, "Username must be at least 3 characters", HttpStatus.BAD_REQUEST),
    PASSWORD_INVALID(1004, "Password must be at least 8 characters", HttpStatus.BAD_REQUEST),
    USER_NOT_EXISTED(1005, "User not existed !!!", HttpStatus.NOT_FOUND),
    UNAUTHENTICATED(1006, "Unauthenticated !!!", HttpStatus.UNAUTHORIZED),
    INVALID_HEADER_JWT(9998, "INVALID_HEADER_JWT !!!", HttpStatus.UNAUTHORIZED)
    ;
    ErrorCode(int code, String message, HttpStatusCode statusCode) {
        this.code = code;
        this.message = message;
        this.statusCode = statusCode;
    }

    private int code;
    private String message;
    private HttpStatusCode statusCode;

}

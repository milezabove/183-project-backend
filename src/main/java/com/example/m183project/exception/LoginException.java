package com.example.m183project.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class LoginException extends RuntimeException{
    private final HttpStatus httpStatus;

    public LoginException(String message, HttpStatus httpStatus) {
        super(message);
        this.httpStatus = httpStatus;
    }

}

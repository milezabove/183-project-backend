package com.example.m183project.exception;

import com.example.m183project.service.dto.ErrorDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler(value = { LoginException.class })
    @ResponseBody
    public ResponseEntity<ErrorDTO> handleException(LoginException ex) {
        return ResponseEntity
                .status(ex.getHttpStatus())
                .body(new ErrorDTO(ex.getMessage()));
    }
}

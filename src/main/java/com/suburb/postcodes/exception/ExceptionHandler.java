package com.suburb.postcodes.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ResponseStatus;

@Slf4j
@ControllerAdvice
public class ExceptionHandler {
    private static final String ERROR_MESSAGE = "Caught unexpected exception";

    @org.springframework.web.bind.annotation.ExceptionHandler({MethodArgumentNotValidException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    protected ResponseEntity<Void> methodArgumentNotValidExceptionHandler(MethodArgumentNotValidException e) {
        log.error(ERROR_MESSAGE, e);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @org.springframework.web.bind.annotation.ExceptionHandler({IllegalArgumentException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    protected ResponseEntity<Void> illegalArgumentExceptionHandler(IllegalArgumentException e) {
        log.error(ERROR_MESSAGE, e);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }
}

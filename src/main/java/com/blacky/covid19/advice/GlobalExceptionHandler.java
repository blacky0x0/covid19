package com.blacky.covid19.advice;

import com.github.lianjiatech.retrofit.spring.boot.exception.RetrofitException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.lang.reflect.UndeclaredThrowableException;
import java.time.LocalDateTime;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = {RuntimeException.class})
    protected ResponseEntity<Object> handleExceptions(RuntimeException ex, WebRequest request) {
        var response = ExceptionMessage.builder()
                .status(HttpStatus.SERVICE_UNAVAILABLE.value())
                .message("Oops! Something went wrong. Please try again or report an issue")
                .timestamp(LocalDateTime.now().toString())
                .build();
        log.error("Exception: {}", response, ex);
        return new ResponseEntity<>(response, HttpStatus.SERVICE_UNAVAILABLE);
    }

    @ExceptionHandler(value = {IllegalArgumentException.class})
    protected ResponseEntity<Object> handleIllegalArgumentException(IllegalArgumentException ex, WebRequest request) {
        var response = ExceptionMessage.builder()
                .status(HttpStatus.BAD_REQUEST.value())
                .message(ex.getMessage())
                .timestamp(LocalDateTime.now().toString())
                .build();
        log.error("Exception: {}", response, ex);
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = {UndeclaredThrowableException.class, RetrofitException.class})
    protected ResponseEntity<Object> handleUndeclaredOrRetrofitException(RuntimeException ex, WebRequest request) {
        var response = ExceptionMessage.builder()
                .status(HttpStatus.TOO_MANY_REQUESTS.value())
                .message("HTTP 429 Too Many Requests")
                .timestamp(LocalDateTime.now().toString())
                .build();
        log.error("Exception: {}", response, ex);
        return new ResponseEntity<>(response, HttpStatus.TOO_MANY_REQUESTS);
    }
    
}

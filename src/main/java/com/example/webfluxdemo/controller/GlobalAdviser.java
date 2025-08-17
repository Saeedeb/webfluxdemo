package com.example.webfluxdemo.controller;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.support.WebExchangeBindException;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalAdviser {

    @ExceptionHandler(RuntimeException.class)
    public Mono<ResponseEntity<Map<String, String>>> handleRuntimeException(RuntimeException ex) {
        Map<String, String> error = new HashMap<>();
        error.put("error", ex.getMessage());
        return Mono.just(ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(error));
    }
    @ExceptionHandler(WebExchangeBindException.class)
    public Mono<ResponseEntity<String>> handleValidationException(WebExchangeBindException ex) {
        StringBuilder errors = new StringBuilder();
        ex.getFieldErrors().forEach(error -> {
            errors.append(error.getField()).append(": ").append(error.getDefaultMessage()).append("; ");
        });
        return Mono.just(ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors.toString()));
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<String> handleDataIntegrityViolation(DataIntegrityViolationException ex) {
        StringBuilder errors = new StringBuilder();
        if (ex.getCause() != null && ex.getCause().getMessage().contains("users_email_idx")) {
            errors.append("ایمیل وارد شده قبلا ثبت شده است.");
            return ResponseEntity
                    .status(HttpStatus.CONFLICT)
                    .body(errors.toString());
        }
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("خطای دیتابیس: " + ex.getMessage());
    }

}

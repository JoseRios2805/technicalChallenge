package com.challenge.technicalChallenge.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(NoPercentageAvailableException.class)
    public ResponseEntity<Map<String, String>> handleNoPercentage(NoPercentageAvailableException ex) {
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
                .body(Map.of("error", "PERCENTAGE_UNAVAILABLE", "message", ex.getMessage()));
    }
}
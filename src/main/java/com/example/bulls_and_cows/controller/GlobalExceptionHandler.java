package com.example.bulls_and_cows.controller;

import com.example.bulls_and_cows.service.GameService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(GameService.GameNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleGameNotFound(GameService.GameNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(Map.of("error", ex.getMessage()));
    }

    @ExceptionHandler(GameService.GameNotActiveException.class)
    public ResponseEntity<Map<String, String>> handleGameNotActive(GameService.GameNotActiveException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(Map.of("error", ex.getMessage()));
    }

    @ExceptionHandler(GameService.InvalidGuessException.class)
    public ResponseEntity<Map<String, String>> handleInvalidGuess(GameService.InvalidGuessException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(Map.of("error", ex.getMessage()));
    }
}

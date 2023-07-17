package com.example.xtramileapplicationtest.controller;

import com.example.xtramileapplicationtest.model.Response;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

@RestControllerAdvice
public class ErrorController {

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Response<String>> constraintViolationException(ConstraintViolationException e) {
        return ResponseEntity.badRequest().body(Response.<String>builder()
                .error(e.getMessage())
                .build());
    }

    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<Response<String>> apiException(ResponseStatusException e) {
        return ResponseEntity.status(e.getStatusCode()).body(Response.<String>builder()
                .error(e.getMessage())
                .build());
    }
}

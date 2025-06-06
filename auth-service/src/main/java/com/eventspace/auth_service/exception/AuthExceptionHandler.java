package com.eventspace.auth_service.exception;

import com.eventspace.auth_service.dtos.ExceptionResponse;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class AuthExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    private ResponseEntity<Map<String, String>> handleValidationError(MethodArgumentNotValidException ex){
        Map<String, String> errors = new HashMap<>();

        ex.getBindingResult().getFieldErrors().forEach(error ->
                errors.put(error.getField(), error.getDefaultMessage())
        );

        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(EntityExistsException.class)
    private ResponseEntity<ExceptionResponse> handleIntegrityViolationError(EntityExistsException ex){
        ExceptionResponse response = ExceptionResponse.builder().message(ex.getMessage())
                                     .timestamp(LocalDateTime.now()).status(String.valueOf(HttpStatus.CONFLICT.value())).build();
        return new ResponseEntity<>(response, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    private ResponseEntity<ExceptionResponse> handleIntegrityViolationError(EntityNotFoundException ex){
        ExceptionResponse response = ExceptionResponse.builder().message(ex.getMessage())
                .timestamp(LocalDateTime.now()).status(String.valueOf(HttpStatus.NOT_FOUND.value())).build();
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }


}

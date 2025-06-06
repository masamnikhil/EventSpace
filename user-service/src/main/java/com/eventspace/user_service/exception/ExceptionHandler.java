package com.eventspace.user_service.exception;

import com.eventspace.user_service.dtos.ExceptionResponse;
import jakarta.persistence.EntityExistsException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class ExceptionHandler {

    private ResponseEntity<ExceptionResponse> handleEntityExistsError(EntityExistsException ex){
        ExceptionResponse response = ExceptionResponse.builder().message(ex.getMessage())
                .timestamp(LocalDateTime.now()).statusCode(HttpStatus.FOUND.value()).build();
        return new ResponseEntity<>(response, HttpStatus.FOUND);
    }
 }

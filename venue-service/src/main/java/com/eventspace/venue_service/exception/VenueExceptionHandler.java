package com.eventspace.venue_service.exception;

import com.eventspace.venue_service.dtos.ExceptionResponse;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class VenueExceptionHandler {

    @ExceptionHandler(EntityExistsException.class)
    public ResponseEntity<ExceptionResponse> entityExistException(EntityExistsException ex){
        ExceptionResponse response = ExceptionResponse.builder().message(ex.getMessage())
                .statusCode(HttpStatus.CONFLICT.value()).timestamp(LocalDateTime.now()).build();
        return new ResponseEntity<>(response, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ExceptionResponse> entityNotExistException(EntityNotFoundException ex){
        ExceptionResponse response = ExceptionResponse.builder().message(ex.getMessage())
                .statusCode(HttpStatus.NOT_FOUND.value()).timestamp(LocalDateTime.now()).build();
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

}

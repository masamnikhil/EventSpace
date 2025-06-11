package com.eventspace.booking_service.exception;

import com.eventspace.booking_service.dtos.ExceptionResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class BookingExceptionHandler {

    @ExceptionHandler(VenueAlreadyBookedException.class)
    public ResponseEntity<ExceptionResponse> handleVenueBookingError(VenueAlreadyBookedException ex){
        ExceptionResponse response = ExceptionResponse.builder().message(ex.getMessage())
                .timestamp(LocalDateTime.now()).statusCode(HttpStatus.CONFLICT.value()).build();
        return new ResponseEntity<>(response, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(VenueNotAvailableException.class)
    public ResponseEntity<ExceptionResponse> handleVenueBookingError(VenueNotAvailableException ex){
        ExceptionResponse response = ExceptionResponse.builder().message(ex.getMessage())
                .timestamp(LocalDateTime.now()).statusCode(HttpStatus.CONFLICT.value()).build();
        return new ResponseEntity<>(response, HttpStatus.CONFLICT);
    }
}

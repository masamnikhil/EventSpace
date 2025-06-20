package com.eventspace.booking_service.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@Builder
public class ExceptionResponse {

    private String message;
    private Integer statusCode;
    private LocalDateTime timestamp;
}

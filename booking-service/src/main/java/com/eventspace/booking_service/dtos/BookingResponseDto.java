package com.eventspace.booking_service.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class BookingResponseDto {

    private String id;
    private String venueName;
    private String address;
    private String city;
    private String state;
    private LocalDate bookingDate;
    private LocalTime startTime;
    private LocalTime endTime;
}

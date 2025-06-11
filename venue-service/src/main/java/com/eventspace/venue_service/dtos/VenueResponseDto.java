package com.eventspace.venue_service.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class VenueResponseDto {

    private String venueName;
    private String address;
    private String city;
    private String state;
    private LocalTime availableFrom;
    private LocalTime availableTo;
}

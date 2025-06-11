package com.eventspace.venue_service.dtos;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class VenueRequest {

    @NotBlank(message = "name should not be empty")
    private String name;
    private String description;
    @NotBlank(message = "address should not be empty")
    private String address;
    @NotBlank(message = "city should not be empty")
    private String city;
    @NotBlank(message = "state should not be empty")
    private String state;
    @NotBlank(message = "pincode should not be empty")
    private String pincode;
    @NotNull(message = "capacity should not be null")
    @Min(value = 50, message = "capacity should be atleast 1")
    private Long capacity;
    @NotNull(message = "price should not be null")
    @Min(value = 50, message = "price should be atleast 50")
    private Long bookingPrice;
    @NotNull(message = "open at should not be empty")
    private LocalTime availableFrom;
    @NotNull(message = "close at should not be empty")
    private LocalTime availableTo;
}

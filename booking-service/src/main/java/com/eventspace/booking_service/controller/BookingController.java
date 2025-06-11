package com.eventspace.booking_service.controller;

import com.eventspace.booking_service.Model.Booking;
import com.eventspace.booking_service.dtos.BookingResponseDto;
import com.eventspace.booking_service.service.BookingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/bookings")
public class BookingController {

    private final BookingService bookingService;

    @GetMapping("/bookvenue")
    public ResponseEntity<BookingResponseDto> createBooking(@RequestBody Booking booking){
        BookingResponseDto booked = bookingService.bookVenue(booking);
        if(booked != null)
            return new ResponseEntity<>(booked,HttpStatus.CREATED);
        else
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
}

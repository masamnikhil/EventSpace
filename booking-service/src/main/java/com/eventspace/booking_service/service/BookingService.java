package com.eventspace.booking_service.service;

import com.eventspace.booking_service.Model.Booking;
import com.eventspace.booking_service.dtos.BookingResponseDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface BookingService {

    BookingResponseDto bookVenue(Booking booking);
    boolean deleteBooking(String id);
    boolean updateBookingByStatus(String id);
    List<Booking> getBookingsbyVenueId(Long venueId);
}

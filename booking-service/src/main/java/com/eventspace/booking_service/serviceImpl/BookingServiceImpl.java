package com.eventspace.booking_service.serviceImpl;

import com.eventspace.booking_service.Model.Booking;
import com.eventspace.booking_service.Model.Status;
import com.eventspace.booking_service.dtos.BookingResponseDto;
import com.eventspace.booking_service.dtos.VenueResponseDto;
import com.eventspace.booking_service.exception.VenueAlreadyBookedException;
import com.eventspace.booking_service.exception.VenueNotAvailableException;
import com.eventspace.booking_service.feignclient.VenueClient;
import com.eventspace.booking_service.repository.BookingRepository;
import com.eventspace.booking_service.service.BookingService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class BookingServiceImpl implements BookingService {

    private final BookingRepository bookingRepository;

    private final VenueClient venueClient;

    @Override
    public BookingResponseDto bookVenue(Booking booking) {
       boolean isAvailable = isVenueAvailable(booking.getVenueId(), booking.getBookingDate()
               , booking.getStartTime(), booking.getEndTime());
        VenueResponseDto venueResponse = venueClient.getVenueInfo(booking.getVenueId());
        boolean isOpenTiming = isVenueAvailableTime(venueResponse.getAvailableFrom(), venueResponse.getAvailableTo(), booking.getStartTime(), booking.getEndTime());

       if(!isAvailable){
           throw new VenueAlreadyBookedException("venue already booked");
       }
       if(!isOpenTiming)
           throw new VenueNotAvailableException("venue will be closed.");
       try {
           booking.setStatus(Status.CONFIRMED);
           Booking booking1 = bookingRepository.save(booking);
           BookingResponseDto bookingResponse = BookingResponseDto.builder().bookingDate(booking1.getBookingDate()).id(booking1.getId()).startTime(booking1.getStartTime())
                   .endTime(booking1.getEndTime()).venueName(venueResponse.getVenueName()).address(venueResponse.getAddress()).city(venueResponse.getCity()).state(venueResponse.getState()).build();
       return bookingResponse;
       } catch (Exception e) {
           throw new RuntimeException(e);
       }
    }

    private boolean isVenueAvailableTime(LocalTime availableFrom, LocalTime availableTo, LocalTime startTime, LocalTime endTime) {

        if(startTime.isBefore(availableFrom) && endTime.isAfter(availableTo))
            return false;
        else
            return true;
    }


    @Override
    public boolean deleteBooking(String id) {
        return false;
    }

    @Override
    public boolean updateBookingByStatus(String id) {
        return false;
    }

    private boolean isVenueAvailable(Long venueId, LocalDate bookingDate, LocalTime requestedStartTime, LocalTime requestedEndTime){
        Optional<List<Booking>> bookings = bookingRepository.findByVenueIdAndBookingDateAndStatus(venueId, bookingDate, Status.CONFIRMED);
        List<Booking> bookingList= bookings.get().stream().filter(booking -> !requestedStartTime.isAfter(booking.getEndTime()) && !booking.getStartTime().isAfter(requestedEndTime))
                .collect(Collectors.toList());
        if(bookingList.isEmpty())
            return true;
        return false;
    }

    @Override
    public List<Booking> getBookingsbyVenueId(Long venueId) {
            return null;
    }
}

package com.eventspace.booking_service.repository;

import com.eventspace.booking_service.Model.Booking;
import com.eventspace.booking_service.Model.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface BookingRepository extends JpaRepository<Booking, String> {

    Optional<List<Booking>> findByVenueIdAndBookingDateAndStatus(Long venueId, LocalDate date, Status status);
}

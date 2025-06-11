package com.eventspace.booking_service.exception;

public class VenueNotAvailableException extends RuntimeException {
    public VenueNotAvailableException(String message) {
        super(message);
    }
}

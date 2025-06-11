package com.eventspace.booking_service.exception;

public class VenueAlreadyBookedException extends RuntimeException{

    public VenueAlreadyBookedException(String msg){
        super(msg);
    }
}

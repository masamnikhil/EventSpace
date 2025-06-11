package com.eventspace.venue_service.service;

import com.eventspace.venue_service.dtos.VenueResponseDto;
import com.eventspace.venue_service.model.Venue;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public interface VenueService {

    @Transactional
    boolean createVenue(Venue venue) throws Exception;
    @Transactional
    boolean deleteVenue(Long venueId);
    Venue getVenueInfo(Long id);
    List<Venue> getAllVenues();
    VenueResponseDto getVenue(Long id);

}

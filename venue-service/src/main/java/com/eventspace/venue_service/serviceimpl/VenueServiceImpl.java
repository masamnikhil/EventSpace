package com.eventspace.venue_service.serviceimpl;

import com.eventspace.venue_service.config.JacksonConfig;
import com.eventspace.venue_service.dtos.VenueResponseDto;
import com.eventspace.venue_service.model.Venue;
import com.eventspace.venue_service.repository.VenueRepository;
import com.eventspace.venue_service.service.VenueService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class VenueServiceImpl implements VenueService {

    private final VenueRepository venueRepository;

    private static final ObjectMapper mapper = JacksonConfig.getMapper();

    @Override
    public boolean createVenue(Venue venue) throws Exception {
        Optional<Venue> existingVenue = venueRepository.findByName(venue.getName());
        if(existingVenue.isPresent()) {
            Venue venue1 = existingVenue.get();
            if (compareByJson(existingVenue, venue1))
                throw new EntityExistsException("venue Already exists");
        }
            try{
                venueRepository.save(venue);
                return true;
            } catch (Exception e) {
                return false;
            }
    }

    public static boolean compareByJson(Object obj1, Object obj2) throws Exception {
        String json1 = mapper.writeValueAsString(obj1);
        String json2 = mapper.writeValueAsString(obj2);
        return json1.equals(json2);
    }

    @Override
    public boolean deleteVenue(Long venueId) {
        return false;
    }

    @Override
    public Venue getVenueInfo(Long id) {
        Optional<Venue> venue = venueRepository.findById(id);
        if(venue.isEmpty())
            throw new EntityNotFoundException("Venue not found with id " + id);
        return venue.get();
    }

    @Override
    public List<Venue> getAllVenues() {
        List<Venue> venueList;
        venueList = venueRepository.findAll();
        if(venueList.isEmpty())
            throw new EntityNotFoundException("Venues not found");

        return venueList;
    }

    @Override
    public VenueResponseDto getVenue(Long id){
        Optional<Venue> optionalVenue = venueRepository.findById(id);
        if(optionalVenue.isPresent()){
            Venue venue = optionalVenue.get();
            VenueResponseDto venueResponseDto = VenueResponseDto.builder().venueName(venue.getName())
                    .address(venue.getAddress()).city(venue.getCity()).state(venue.getState()).availableFrom(venue.getAvailableFrom()).availableTo(venue.getAvailableTo()).build();
            return venueResponseDto;
        }
        throw new EntityNotFoundException("Venue not found with id " + id);
    }
}

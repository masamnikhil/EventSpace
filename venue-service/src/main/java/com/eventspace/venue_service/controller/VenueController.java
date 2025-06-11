package com.eventspace.venue_service.controller;

import com.eventspace.venue_service.dtos.VenueRequest;
import com.eventspace.venue_service.dtos.VenueResponseDto;
import com.eventspace.venue_service.model.Venue;
import com.eventspace.venue_service.service.VenueService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/venues")
@RequiredArgsConstructor
public class VenueController {

    private final VenueService venueService;

    @PostMapping("/createvenue")
    private ResponseEntity<String> createVenue(@Valid @RequestBody VenueRequest venue) throws Exception {
        Venue newVenue = Venue.builder().name(venue.getName()).description(venue.getDescription())
                .capacity(venue.getCapacity()).address(venue.getAddress()).city(venue.getCity()).state(venue.getState())
                .bookingPrice(venue.getBookingPrice()).pincode(venue.getPincode()).availableFrom(venue.getAvailableFrom())
                .availableTo(venue.getAvailableTo()).build();
     if(venueService.createVenue(newVenue))
         return new ResponseEntity<>("Venue Successfully Created", HttpStatus.CREATED);
     else
         return new ResponseEntity<>("something went wrong, please try again", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @GetMapping()
    private ResponseEntity<List<Venue>> getAllVenues(){
        List<Venue> venues = venueService.getAllVenues();
        if(!venues.isEmpty())
            return ResponseEntity.ok(venues);
        else
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @GetMapping("/{id}")
    private ResponseEntity<List<Venue>> getVenueById(@PathVariable(name = "id", required = false) Long id){
        List<Venue> venues;
        if(id == null){
            venues = venueService.getAllVenues();
            return new ResponseEntity<>(venues, HttpStatus.OK);
        }
        else
            return ResponseEntity.ok(List.of(venueService.getVenueInfo(id)));
    }

    @GetMapping("/venue/{id}")
    public ResponseEntity<VenueResponseDto> getVenue(@PathVariable(name = "id") Long id){
        VenueResponseDto venueResponseDto = venueService.getVenue(id);
        if(venueResponseDto != null)
            return new ResponseEntity<>(venueResponseDto, HttpStatus.OK);

        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
}

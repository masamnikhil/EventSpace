package com.eventspace.booking_service.feignclient;

import com.eventspace.booking_service.config.FeignConfig;
import com.eventspace.booking_service.dtos.VenueResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "VENUE-SERVICE", configuration = FeignConfig.class)
public interface VenueClient {

    @GetMapping("/venues/venue/{id}")
    VenueResponseDto getVenueInfo(@PathVariable Long id);
}

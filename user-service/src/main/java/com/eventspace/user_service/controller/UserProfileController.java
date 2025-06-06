package com.eventspace.user_service.controller;

import com.eventspace.user_service.model.UserProfile;
import com.eventspace.user_service.service.UserProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserProfileController {

    private final UserProfileService userProfileService;

    private ResponseEntity<String> createProfile(@RequestBody UserProfile userProfile){
        if(userProfileService.createProfile(userProfile))
            return new ResponseEntity<>("Profile Successfully Created", HttpStatus.CREATED);
        else
            return new ResponseEntity<>("something went wrong", HttpStatus.INTERNAL_SERVER_ERROR);
    }
}

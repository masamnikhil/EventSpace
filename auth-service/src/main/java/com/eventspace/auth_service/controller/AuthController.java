package com.eventspace.auth_service.controller;

import com.eventspace.auth_service.dtos.LoginRequest;
import com.eventspace.auth_service.dtos.UserInfoDto;
import com.eventspace.auth_service.dtos.UserResponseDto;
import com.eventspace.auth_service.kafkaconfig.KafkaProducer;
import com.eventspace.auth_service.model.User;
import com.eventspace.auth_service.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    private final PasswordEncoder passwordEncoder;


    @PostMapping("/signup")
    public ResponseEntity<String> registerUser(@Valid @RequestBody UserInfoDto user){
           if(authService.userSignUp(user))
               return new ResponseEntity<>("Signed Up Successfully", HttpStatus.CREATED);
           else
           return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @GetMapping("/login")
    public ResponseEntity<UserResponseDto> authenticate(@Valid @RequestBody LoginRequest loginRequest){
           UserResponseDto userResponse = authService.UserLogin(loginRequest.getUsername(), loginRequest.getPassword());
           return ResponseEntity.ok(userResponse);
    }

    @PatchMapping("/updatepassword")
    public ResponseEntity<String> updatePassword(@RequestParam(name = "username") String username,
                                                 @RequestParam(name = "oldpassword") String oldPassword,
                                                 @RequestParam(name = "newpassword") String newPassword){
        if(authService.updatePassword(username, oldPassword, newPassword))
        return new ResponseEntity<>("Password changed Successfully", HttpStatus.ACCEPTED);
        else
            return new ResponseEntity<>("error occurred, try again", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @PatchMapping("/updateusername")
    public ResponseEntity<String> updatePassword(@RequestParam(name = "username") String username,
                                                 @RequestParam(name = "newusername") String newUsername)
        {
        if(authService.updateUsername(username, newUsername))
            return new ResponseEntity<>("Username changed Successfully", HttpStatus.ACCEPTED);
        else
            return new ResponseEntity<>("error occurred, try again", HttpStatus.INTERNAL_SERVER_ERROR);
    }
}

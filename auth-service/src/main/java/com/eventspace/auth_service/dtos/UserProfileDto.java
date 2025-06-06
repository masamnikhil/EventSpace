package com.eventspace.auth_service.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class UserProfileDto {

    private String username;
    private String firstname;
    private String lastname;
    private String address;
    private String mobileNumber;
}

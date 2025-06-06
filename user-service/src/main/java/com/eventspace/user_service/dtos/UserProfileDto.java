package com.eventspace.user_service.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class UserProfileDto {

    private String username;
    private String firstname;
    private String lastname;
    private String address;
    private String mobileNumber;
}

package com.eventspace.auth_service.dtos;

import com.eventspace.auth_service.model.Role;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.Set;

@Data
@AllArgsConstructor
@Builder
public class UserInfoDto {

    @NotBlank(message = "username is required")
    private String username;
    @NotBlank(message = "password is required")
    private String password;
    @NotBlank(message = "email is required")
    private String email;
    @NotEmpty(message = "role's are mandatory")
    private Set<Role> roles;
    @NotBlank(message = "firstname is required")
    private String firstname;
    @NotBlank(message = "lastname is required")
    private String lastname;
    private String address;
    @NotBlank(message = "mobile number is required")
    private String mobileNumber;
}

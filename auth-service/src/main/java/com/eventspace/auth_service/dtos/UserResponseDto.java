package com.eventspace.auth_service.dtos;

import com.eventspace.auth_service.model.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.Set;

@AllArgsConstructor
@Data
@Builder
public class UserResponseDto {

    private String username;
    private Set<Role> roles;
    private String token;
}

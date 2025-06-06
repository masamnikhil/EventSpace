package com.eventspace.auth_service.service;

import com.eventspace.auth_service.dtos.UserInfoDto;
import com.eventspace.auth_service.dtos.UserResponseDto;
import com.eventspace.auth_service.model.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public interface AuthService {

    boolean userSignUp(UserInfoDto user);
    UserResponseDto UserLogin(String username, String password);
    boolean updateUsername(String existingUsername, String newUsername);
    boolean updatePassword(String username, String newPassword, String oldPassword);
}

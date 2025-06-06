package com.eventspace.user_service.service;

import com.eventspace.user_service.model.UserProfile;
import org.springframework.stereotype.Service;

@Service
public interface UserProfileService {

    boolean createProfile(UserProfile userProfile);
    UserProfile getUserProfile(String username);
    boolean deleteUserProfile(String username);
    boolean updateName(String username);
}

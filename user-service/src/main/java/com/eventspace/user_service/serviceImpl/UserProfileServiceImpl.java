package com.eventspace.user_service.serviceImpl;

import com.eventspace.user_service.model.UserProfile;
import com.eventspace.user_service.repository.UserProfileRepository;
import com.eventspace.user_service.service.UserProfileService;
import jakarta.persistence.EntityExistsException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserProfileServiceImpl implements UserProfileService {

    private final UserProfileRepository userProfileRepository;

    @Override
    public boolean createProfile(UserProfile userProfile) {
        boolean existUser = userProfileRepository.existsByUsername(userProfile.getUsername());
        if(existUser)
            throw new EntityExistsException("username is taken");
        try{
            userProfileRepository.save(userProfile);
            return true;
        }catch (Exception ex){
            return false;
        }
    }

    @Override
    public UserProfile getUserProfile(String username) {
        return null;
    }

    @Override
    public boolean deleteUserProfile(String username) {
        return false;
    }

    @Override
    public boolean updateName(String name) {
        return false;
    }
}

package com.eventspace.auth_service.serviceImpl;

import com.eventspace.auth_service.config.JWTUtil;
import com.eventspace.auth_service.dtos.UserInfoDto;
import com.eventspace.auth_service.dtos.UserProfileDto;
import com.eventspace.auth_service.dtos.UserResponseDto;
import com.eventspace.auth_service.kafkaconfig.KafkaProducer;
import com.eventspace.auth_service.model.User;
import com.eventspace.auth_service.repository.AuthRepository;
import com.eventspace.auth_service.service.AuthService;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final AuthRepository authRepository;

    private final AuthenticationManager authenticationManager;

    private final JWTUtil jwtUtil;

    private final PasswordEncoder passwordEncoder;

    private final KafkaProducer kafkaProducer;

    @Override
    public boolean userSignUp(UserInfoDto userInfoDto) {
        boolean existingUsername = authRepository.existsByUsername(userInfoDto.getUsername());
        boolean existingEmail = authRepository.existsByEmail(userInfoDto.getEmail());
        if(existingUsername)
            throw new EntityExistsException("username is taken, try with other");
        if(existingEmail)
            throw new EntityExistsException("email is taken, try with other");
          try{
              User user = User.builder().email(userInfoDto.getEmail())
                      .password(passwordEncoder.encode(userInfoDto.getPassword()))
                      .username(userInfoDto.getUsername())
                      .roles(userInfoDto.getRoles()).build();
              User savedUser = authRepository.save(user);
              UserProfileDto userProfileDto = UserProfileDto.builder().firstname(userInfoDto.getFirstname())
                      .lastname(userInfoDto.getLastname())
                      .username(savedUser.getUsername()).mobileNumber(userInfoDto.getMobileNumber()).build();
              kafkaProducer.sendMessage(userProfileDto);
              return true;
          } catch (DataIntegrityViolationException ex) {
             return false;
          }
    }

    @Override
    public UserResponseDto UserLogin(String username, String password) {
        boolean existingUsername = authRepository.existsByUsername(username);
        if(!existingUsername)
            throw new EntityNotFoundException("username not found");
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (BadCredentialsException ex){
            throw new EntityNotFoundException("incorrect password");
        }
        Optional<User> user = authRepository.findByUsername(username);
        List<String> roles = user.get().getRoles().stream().map(role -> role.toString()).collect(Collectors.toList());
        String token = jwtUtil.generateToken(username, roles);
        return userDtoMapper(user.get(), token);
    }

    public UserResponseDto userDtoMapper(User user, String token){
        UserResponseDto userResponseDto = UserResponseDto.builder().username(user.getUsername())
                .roles(user.getRoles()).token(token).build();
        return userResponseDto;
    }

    @Override
    public boolean updateUsername(String existingUsername, String newUsername) {
        Optional<User> user = authRepository.findByUsername(existingUsername);
        boolean existingUser = authRepository.existsByUsername(newUsername);
         if(user.isEmpty())
            throw new EntityNotFoundException("username not found");
        if(existingUser)
            throw new EntityExistsException("username is taken already, try with another");
        user.get().setUsername(newUsername);
        try {
            authRepository.save(user.get());
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public boolean updatePassword(String username, String oldPassword, String newPassword) {
        Optional<User> user = authRepository.findByUsername(username);
        if(user.isEmpty())
            throw new EntityNotFoundException("incorrect username");
        if(!passwordEncoder.matches(oldPassword, user.get().getPassword()))
            throw new EntityNotFoundException("old password incorrect");
        user.get().setPassword(passwordEncoder.encode(newPassword));
        try {
            authRepository.save(user.get());
            return true;
        }catch (Exception ex){
            return false;
        }
    }
}

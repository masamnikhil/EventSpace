package com.eventspace.user_service.kafkaconfig;

import com.eventspace.user_service.dtos.UserProfileDto;
import com.eventspace.user_service.model.UserProfile;
import com.eventspace.user_service.service.UserProfileService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class KafkaConsumer {

    private final UserProfileService userProfileService;

    private static Logger logger = LoggerFactory.getLogger(KafkaConsumer.class);

    @KafkaListener(topics = "user-topic", groupId = "user-group")
    public void consume(UserProfileDto userProfileDto) {
        UserProfile userProfile = UserProfile.builder().firstname(userProfileDto.getFirstname())
                .lastname(userProfileDto.getLastname()).username(userProfileDto.getUsername())
                .address(userProfileDto.getAddress()).mobileNumber(userProfileDto.getMobileNumber()).build();
        userProfileService.createProfile(userProfile);
        logger.info("{}", userProfileDto);
    }
}

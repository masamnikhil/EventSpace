package com.eventspace.user_service.kafkaconfig;

import com.eventspace.user_service.dtos.UserProfileDto;
import com.eventspace.user_service.model.UserProfile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaConsumer {

    private static Logger logger = LoggerFactory.getLogger(KafkaConsumer.class);

    @KafkaListener(topics = "user-topic", groupId = "user-group")
    public void consume(UserProfileDto userProfileDto) {
        logger.info("{}", userProfileDto);
    }
}

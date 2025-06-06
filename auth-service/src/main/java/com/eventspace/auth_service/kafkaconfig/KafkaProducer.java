package com.eventspace.auth_service.kafkaconfig;

import com.eventspace.auth_service.dtos.UserProfileDto;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class KafkaProducer {

    private static final String TOPIC = "user-topic";

    private static Logger logger = LoggerFactory.getLogger(KafkaProducer.class);


    private final KafkaTemplate<String, UserProfileDto> kafkaTemplate;

    public void sendMessage(UserProfileDto userProfileDto) {
        kafkaTemplate.send(TOPIC, userProfileDto);
        logger.info("message published successfully");
    }
}

package com.apache.kafka.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.KafkaListener;

@Configuration
public class KafkaConfig {
    private Logger log = LoggerFactory.getLogger(KafkaConfig.class);
    @KafkaListener(topics = AppConstant.LOCATION_TOPIC_NAME, groupId = AppConstant.LOCATION_GROUP_ID)
    public void updateLocation(String value){
        log.info(value);
    }
}

package com.example.auth_module.service.kafka;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class KafkaConsumerService {
    @KafkaListener(topics = "my-topic", groupId = "my-group-id")
    public void consume(String message) {
        log.info("받은 메시지: {}", message);
    }
}

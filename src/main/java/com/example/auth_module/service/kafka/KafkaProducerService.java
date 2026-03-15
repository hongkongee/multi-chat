package com.example.auth_module.service.kafka;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class KafkaProducerService {
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final String TOPIC = "my-topic";

    public void sendMessage(String topic, String message) {
        log.info("보내는 메시지: {}", message);
        kafkaTemplate.send(topic, message);
    }

}

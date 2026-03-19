package com.example.auth_module.service.kafka;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
@ConditionalOnProperty(prefix = "features.kafka", name = "enabled", havingValue = "true", matchIfMissing = true)
public class KafkaProducerService {
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final String TOPIC = "my-topic";

    public void sendMessage(String topic, String message) {
        try {
            log.info("카프카로 메시지 전송 시도: {}", message);
            // .get()을 붙여서 응답이 올 때까지 기다립니다. (에러 확인용)
            kafkaTemplate.send(topic, message).get();
            log.info("카프카 전송 성공!");
        } catch (Exception e) {
            log.error("카프카 전송 중 진짜 에러 발생: {}", e.getMessage());
        }
    }

}

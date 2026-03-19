package com.example.auth_module.api;

import com.example.auth_module.service.kafka.KafkaProducerService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/multichat/v1/kafka")
@RequiredArgsConstructor
@ConditionalOnProperty(prefix = "features.kafka", name = "enabled", havingValue = "true", matchIfMissing = true)
public class KafkaController {
    private final KafkaProducerService producerService;

    @GetMapping("/send")
    public String send(@RequestParam("topic") String topic,
            @RequestParam("message") String message) {
        producerService.sendMessage(topic, message);
        return "카프카로 보낸 메시지: " + message;
    }
}

package com.example.auth_module.api;

import com.example.auth_module.service.kafka.KafkaProducerService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/multichat/v1/kafka")
@RequiredArgsConstructor
public class KafkaController {
    private final KafkaProducerService producerService;

    @GetMapping("/send")
    public String send(@RequestParam("message") String message) {
        producerService.sendMessage(message);
        return "카프카로 보낸 메시지: " + message;
    }
}

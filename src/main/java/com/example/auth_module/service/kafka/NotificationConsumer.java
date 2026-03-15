package com.example.auth_module.service.kafka;

import com.example.auth_module.api.NotificationController;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class NotificationConsumer {
    @KafkaListener(topics = "login-events", groupId = "notification-group")
    public void onLogin(String userId) {
        SseEmitter emitter = NotificationController.emitters.get(userId);
        if (emitter != null) {
            try {
                emitter.send(SseEmitter.event()
                        .name("login-notification")
                        .data("실시간 알림: " + userId + "님, 환영합니다!"));
            } catch (IOException e) {
                NotificationController.emitters.remove(userId);
            }
        }
    }
}

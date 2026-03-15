package com.example.auth_module.api;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@RestController
public class NotificationController {
    public static final Map<String, SseEmitter> emitters = new ConcurrentHashMap<>();

    @CrossOrigin(origins = "http://127.0.0.1:5500")
    @GetMapping(value = "/subscribe/{userId}", produces = "text/event-stream;charset=UTF-8")
    public SseEmitter subscribe(@PathVariable String userId) {
        SseEmitter emitter = new SseEmitter(Long.MAX_VALUE);
        emitters.put(userId, emitter);

        // 연결 종료 시 처리
        emitter.onCompletion(() -> emitters.remove(userId));
        emitter.onTimeout(() -> emitters.remove(userId));

        return emitter;
    }
}

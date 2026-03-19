package com.example.auth_module.service.dataset;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Map;

@Service
public class HuggingFaceClient {
    private final WebClient webClient;

    public HuggingFaceClient(@Value("${hf.token}") String token) {
        this.webClient = WebClient.builder()
                .baseUrl("https://huggingface.co/api")
                .defaultHeader(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                .build();
    }

    // HF에 리포지토리 생성 요청
    public Mono<Void> createDatasetRepo(String repoId) {
        return webClient.post()
                .uri("/datasets")
                .bodyValue(Map.of("name", repoId, "private", true))
                .retrieve()
                .bodyToMono(Void.class);
    }

    public Mono<Void> uploadDatasetFile(String repoId, String fileName, byte[] fileContent) {
        return webClient.post()
                .uri("/{repoId}/upload/main/{fileName}", repoId, fileName)
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .bodyValue(fileContent)
                .retrieve()
                .bodyToMono(Void.class);

    }
}

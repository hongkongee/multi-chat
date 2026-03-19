package com.example.auth_module.service.dataset;

import com.example.auth_module.entity.DatasetMetadata;
import com.example.auth_module.repository.DatasetRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class DatasetService {
    private final DatasetRepository datasetRepository;
    private final HuggingFaceClient hfClient;

    @Transactional
    public DatasetMetadata registerAndUpload(DatasetMetadata metadata, MultipartFile file) throws IOException {
        // 1. HF 리포지토리 먼저 생성
        hfClient.createDatasetRepo(metadata.getHfRepoId()).block();

        // 2. HF에 실제 파일 바이너리 전송
        // 파일명을 원본 이름 그대로 사용하거나 고정된 이름(data.jsonl)으로 설정
        String fileName = file.getOriginalFilename();
        hfClient.uploadDatasetFile(metadata.getHfRepoId(), fileName, file.getBytes()).block();

        // 3. 파일 관련 정보 메타데이터에 업데이트
        metadata.setFormat(extractExtension(fileName));
        metadata.setTotalRows(countLines(file)); // 필요 시 행 수 계산 로직 추가

        // 4. PostgreSQL 저장
        return datasetRepository.save(metadata);
    }

    /**
     * 대용량 파일의 행(Line) 수를 계산합니다.
     * 메모리 효율을 위해 BufferedReader와 스트림을 사용합니다.
     */
    private Long countLines(MultipartFile file) {
        if (file.isEmpty()) return 0L;

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream(), StandardCharsets.UTF_8))) {
            // Java 8+ 스트림을 사용하여 메모리 점유를 최소화하며 줄 수를 셉니다.
            return reader.lines().count();
        } catch (IOException e) {
            log.error("Failed to count lines in file: {}", file.getOriginalFilename(), e);
            return -1L;
        }
    }

    /** 파일 이름에서 확장자 추출 */
    private String extractExtension(String fileName) {
        if (fileName == null || !fileName.contains(".")) {
            return "unknown";
        }
        return fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();    }


}

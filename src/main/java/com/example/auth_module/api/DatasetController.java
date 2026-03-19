package com.example.auth_module.api;

import com.example.auth_module.entity.DatasetMetadata;
import com.example.auth_module.service.dataset.DatasetService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/model/v1/datasets")
@RequiredArgsConstructor
public class DatasetController {

    private final DatasetService datasetService;

    @PostMapping("/upload")
    public ResponseEntity<DatasetMetadata> uploadDataset(
            @RequestPart("file") MultipartFile file,
            @RequestPart("metadata") DatasetMetadata metadata) throws IOException {

        DatasetMetadata savedMetadata = datasetService.registerAndUpload(metadata, file);

        return ResponseEntity.status(HttpStatus.CREATED).body(savedMetadata);
    }


}

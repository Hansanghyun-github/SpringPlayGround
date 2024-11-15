package com.example.springplayground.uploadFile;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.*;
import java.nio.file.Paths;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

@RestController
@Slf4j
@RequiredArgsConstructor
public class FileUploadController {

    private final S3Client s3Client;

    @Value("${aws_s3_bucket}")
    private String bucketName;

    @PostMapping("/upload")
    public String handleFileUpload(@RequestParam("file") MultipartFile file) {
        if (file == null || file.isEmpty() || file.getOriginalFilename() == null) {
            return "File is empty";
        }

        // Read PDF content (if necessary, using libraries like Apache PDFBox for detailed parsing)
        log.info("File Name: {}", file.getOriginalFilename());
        log.info("File Size: {} bytes", file.getSize());

        // try to save the file at S3
        log.info("Uploading file to S3");
        PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                .bucket(bucketName)
                .key("temp/" + UUID.randomUUID() + ".pdf")
                .build();

        // Save the file locally for processing (optional)
        try {
            s3Client.putObject(putObjectRequest, RequestBody.fromInputStream(file.getInputStream(), file.getSize()));
        } catch (IOException e) {
            log.error("Error uploading file to S3", e);
            throw new RuntimeException(e);
        }

        return "File uploaded and logged successfully";
    }
}

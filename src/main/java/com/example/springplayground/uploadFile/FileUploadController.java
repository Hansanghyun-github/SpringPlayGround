package com.example.springplayground.uploadFile;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@Slf4j
public class FileUploadController {

    @PostMapping("/upload")
    public String handleFileUpload(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            return "File is empty";
        }

        try {
            // Read PDF content (if necessary, using libraries like Apache PDFBox for detailed parsing)
            log.info("File Name: {}", file.getOriginalFilename());
            log.info("File Size: {} bytes", file.getSize());

            // Save the file locally for processing (optional)
            File tempFile = File.createTempFile("uploaded", ".pdf");
            file.transferTo(tempFile);
            log.info("Temporary file saved at: {}", tempFile.getAbsolutePath());

            // Clean up the temporary file (optional)
            tempFile.deleteOnExit();
        } catch (IOException e) {
            log.error("Error processing file", e);
            return "Error processing file";
        }

        return "File uploaded and logged successfully";
    }
}

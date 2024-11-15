package com.example.springplayground.uploadFile;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@Slf4j
public class FormController {

    @GetMapping("/uploadForm")
    public String uploadForm() {
        log.info("Loading upload form");
        return "upload-form";
    }
}

package com.example.springplayground;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cache.annotation.EnableCaching;

@ServletComponentScan
@EnableCaching
@SpringBootApplication
public class SpringPlayGroundApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringPlayGroundApplication.class, args);
    }

}

package com.example.springplayground.beanDuplicate;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@TestConfiguration
public class MyTestConfiguration {
    @Bean
    public EmptyInterface emptyInterface() {
        return new TestImplementClass();
    }
}

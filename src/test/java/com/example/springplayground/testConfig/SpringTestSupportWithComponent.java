package com.example.springplayground.testConfig;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicInteger;

@SpringBootTest
public abstract class SpringTestSupportWithComponent {

    static AtomicInteger counter = new AtomicInteger(0);

    @Component
    public static class TestComponent {
        @EventListener(ApplicationReadyEvent.class)
        public void setUp() {
            counter.incrementAndGet();
        }
    }
}

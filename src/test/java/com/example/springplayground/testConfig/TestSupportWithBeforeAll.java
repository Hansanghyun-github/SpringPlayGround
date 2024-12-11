package com.example.springplayground.testConfig;

import org.junit.jupiter.api.BeforeAll;

import java.util.concurrent.atomic.AtomicInteger;

public abstract class TestSupportWithBeforeAll {

    static AtomicInteger counter = new AtomicInteger(0);

    // BeforeAll method is called for each test class
    @BeforeAll
    public static void setup() {
        counter.incrementAndGet();
    }
}

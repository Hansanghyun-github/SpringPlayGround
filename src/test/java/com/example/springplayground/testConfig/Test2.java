package com.example.springplayground.testConfig;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

public class Test2 extends TestSupportWithBeforeAll {

    @Test
    @Order(2)
    void test2() throws Exception {
        assertThat(counter.get()).isEqualTo(2);
    }
}

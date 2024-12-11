package com.example.springplayground.testConfig;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

public class Test1 extends TestSupportWithBeforeAll {

    @Test
    @Order(1)
    void test1() throws Exception {
        assertThat(counter.get()).isEqualTo(1);
    }
}

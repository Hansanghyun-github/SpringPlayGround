package com.example.springplayground.testConfig;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

public class SpringTest2 extends SpringTestSupportWithComponent {
    @Test
    void test2() throws Exception {
        assertThat(counter.get()).isEqualTo(1);
    }
}

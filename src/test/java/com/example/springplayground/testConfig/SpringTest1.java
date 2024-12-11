package com.example.springplayground.testConfig;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

public class SpringTest1 extends SpringTestSupportWithComponent{
    @Test
    void test1() throws Exception {
        Assertions.assertThat(counter.get()).isEqualTo(1);
    }
}

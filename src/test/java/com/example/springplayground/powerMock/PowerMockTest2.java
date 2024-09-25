package com.example.springplayground.powerMock;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

import java.time.LocalDate;

import static org.mockito.Mockito.mockStatic;

public class PowerMockTest2 {

    static MockedStatic<LocalDate> mockLocalDate;
    static LocalDate expected;

    @BeforeAll
    public static void setUp() {
        expected = LocalDate.of(2021, 8, 1);
        mockLocalDate = mockStatic(LocalDate.class);
        mockLocalDate.when(LocalDate::now).thenReturn(expected);
    }

    @AfterAll
    public static void tearDown() {
        mockLocalDate.close();
    }

    @Test
    void power_mock_test() throws Exception {
        // when // then
        Assertions.assertThat(LocalDate.now())
                .isEqualTo(expected);
    }
}

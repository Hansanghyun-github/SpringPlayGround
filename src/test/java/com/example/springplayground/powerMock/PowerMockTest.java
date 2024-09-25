package com.example.springplayground.powerMock;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.*;

public class PowerMockTest {
    TimePrint timePrint = new TimePrint();

    @Test
    void mocking_LocalDate() throws Exception {
        // when // then
        LocalDate now = LocalDate.now();
        LocalDate expected = LocalDate.of(2021, 8, 1);
        try(MockedStatic<LocalDate> mockLocalDate = Mockito.mockStatic(LocalDate.class)){
            mockLocalDate.when(LocalDate::now).thenReturn(expected);

            assertThat(LocalDate.now())
                    .isNotEqualTo(now)
                    .isEqualTo(expected);
        }
    }

    @Test
    void printTime() throws Exception {
        // when // then
        timePrint.printTime();
    }

    @Test
    void print_LocalDateTime_not_Now() throws Exception {
        // given
        LocalDate value = LocalDate.of(2021, 8, 1);

        // when // then
        try(MockedStatic<LocalDate> mockLocalDate = Mockito.mockStatic(LocalDate.class)){
            mockLocalDate.when(LocalDate::now).thenReturn(value);

            timePrint.printTime();
        }
    }

    @Test
    void static_mock_test() throws Exception {
        // when // then
        LocalDate expected = LocalDate.of(2021, 8, 1);
        LocalDate now = LocalDate.now();
        try(MockedStatic<LocalDate> mockLocalDate = Mockito.mockStatic(LocalDate.class)){
            mockLocalDate.when(LocalDate::now).thenReturn(expected);
            assertThat(LocalDate.now()).isNotEqualTo(now);
        }

        assertThat(LocalDate.now())
                .isEqualTo(now);
    }
}

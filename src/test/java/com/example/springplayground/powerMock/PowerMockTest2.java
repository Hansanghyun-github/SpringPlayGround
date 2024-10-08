package com.example.springplayground.powerMock;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

import java.time.LocalDate;
import java.time.LocalDateTime;

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
    void LocalDateTime_now_method_occurs_error_because_of_mocking_LocalDate() throws Exception {
        // when // then
        Assertions.assertThatThrownBy(() -> System.out.println(LocalDateTime.now()))
                .isInstanceOf(NullPointerException.class);
    }

    @Test
    void power_mock_test() throws Exception {
        // when // then
        Assertions.assertThat(LocalDate.now())
                .isEqualTo(expected);
    }

    @Test
    void call_LocalDate_now_method_at_inner_method() throws Exception {
        // given
        TimePrint timePrint = new TimePrint();
        
        // when // then
        timePrint.printTime();
    }
}

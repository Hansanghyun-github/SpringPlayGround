package com.example.springplayground.powerMock;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.Clock;
import java.time.Instant;
import java.time.LocalDate;

import static org.mockito.Mockito.*;

public class ClockMockingTest {
    @Test
    void print_default() throws Exception {
        // given
        TimePrintWithClock timePrintWithClock = new TimePrintWithClock(Clock.systemDefaultZone());

        // when // then
        timePrintWithClock.printTime();
    }

    @Test
    void print_with_mocking_clock() throws Exception {
        // given
        Clock clock = spy(Clock.class);
        when(clock.instant()).thenReturn(Instant.parse("2021-08-01T00:00:00Z"));
        when(clock.getZone()).thenReturn(Clock.systemDefaultZone().getZone());
        TimePrintWithClock timePrintWithClock = new TimePrintWithClock(clock);

        // when // then
        timePrintWithClock.printTime();
    }
}

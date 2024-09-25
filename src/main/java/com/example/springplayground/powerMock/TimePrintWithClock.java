package com.example.springplayground.powerMock;

import lombok.RequiredArgsConstructor;

import java.time.Clock;
import java.time.LocalDate;

@RequiredArgsConstructor
public class TimePrintWithClock {

    private final Clock clock;
    public void printTime() {
        System.out.println("Time is: " + LocalDate.now(clock));
    }
}

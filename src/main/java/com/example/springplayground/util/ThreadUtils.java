package com.example.springplayground.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class ThreadUtils {
    public static List<String> getAllThreadNames() {
        Set<Thread> threadSet = Thread.getAllStackTraces().keySet();
        return threadSet.stream()
                .map(Thread::getName)
                .toList();
    }

    public static List<String> getThreadNamesOf(String threadName) {
        Set<Thread> threadSet = Thread.getAllStackTraces().keySet();
        return threadSet.stream()
                .map(Thread::getName)
                .filter(name -> name.contains(threadName))
                .toList();
    }

}

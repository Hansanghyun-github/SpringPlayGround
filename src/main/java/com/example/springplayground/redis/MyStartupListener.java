package com.example.springplayground.redis;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.cache.CacheManager;
import org.springframework.context.event.EventListener;

@RequiredArgsConstructor
public class MyStartupListener {
    private final CacheManager cacheManager;

    @EventListener(ApplicationReadyEvent.class)
    public void onApplicationReadyEvent() {
        System.out.println("Application ready event");
        System.out.println(cacheManager.getClass().getName());
    }
}

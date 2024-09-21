package com.example.springplayground.afterbooting;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.cache.CacheManager;
import org.springframework.context.event.EventListener;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MyStartupListener {
    private final CacheManager cacheManager;

    @EventListener(ApplicationReadyEvent.class)
    public void onApplicationReadyEvent() {
        System.out.println("Application ready event");
        System.out.println(cacheManager.getClass().getName());
    }
}

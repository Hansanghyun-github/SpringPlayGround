package com.example.springplayground.redis;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequiredArgsConstructor
public class RedisController {
    private final RedisService redisService;
    private final CachingService cachingService;

    @GetMapping("/redis/save")
    public void save(
            @RequestParam("key") String key,
            @RequestParam("value") String value
    ) {
        redisService.save(key, value);
    }

    @GetMapping("/redis/get")
    public String get(@RequestParam("key") String key) {
        return redisService.get(key);
    }

    @GetMapping("/redis/keys")
    public String getKeys(@RequestParam(name = "pattern", required = false) String pattern) {
        if(!StringUtils.hasText(pattern)) {
            pattern = "*";
        }
        return redisService.getKeys(pattern).toString();
    }

    @GetMapping("/cache/get")
    public String getCache() {
        log.info("getCache() called");
        return cachingService.getString();
    }

    @GetMapping("/cache/key")
    public String getCacheKey(@RequestParam("key") Integer key) {
        log.info("getCacheKey() called");
        return cachingService.getKey(key);
    }

    @GetMapping("/cache/object")
    public Object getCacheObject(@RequestParam("name") String name, @RequestParam("age") int age) {
        log.info("getCacheObject() called");
        return cachingService.getObject(name, age);
    }

    @GetMapping("/cache/objects")
    public Object getCacheObjects() {
        log.info("getCacheObjects() called");
        return cachingService.getObjects();
    }

}
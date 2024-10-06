package com.example.springplayground.redis;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Set;

@Service
@Slf4j
@RequiredArgsConstructor
public class RedisService {
    private final RedisTemplate<String, Object> redisTemplate;

    public void save(String key, String value) {
        redisTemplate.opsForValue().set(key, value);
    }

    public String get(String key) {
        return redisTemplate.opsForValue().get(key).toString();
    }

    public Set<String> getKeys(String pattern) {
        return redisTemplate.keys(pattern);
    }

    public Set<String> getKeys() {
        return getKeys("*");
    }

    public Integer setHash(String key, String hashKey, Integer value) {
        return redisTemplate.opsForHash().putIfAbsent(key, hashKey, value) ? 1 : 0;
    }

    public String getHash(String key, String hashKey) {
        Object o = redisTemplate.opsForHash().get(key, hashKey);
        System.out.println(o);
        return o.toString();
    }

    public void incrementHash(String key, String hashKey, Long value) {
        redisTemplate.opsForHash().increment(key, hashKey, value);
    }

    public void decrementHash(String key, String hashKey, Long value) {
        redisTemplate.opsForHash().increment(key, hashKey, -value);
    }

    public void setHashAll(String key) {
        Map<String, Object> map = Map.of("key1", "1", "key2", "2");
        redisTemplate.opsForHash().putAll(key, map);
    }
}

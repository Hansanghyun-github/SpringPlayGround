package com.example.springplayground.redis;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
public class TransactionalRedis {
    private final RedisTemplate<String, Object> customRedisTemplate;

    public void set(String key, Object value) {
        customRedisTemplate.opsForValue().set(key, value);
    }

    public Object get(String key) {
        return customRedisTemplate.opsForValue().get(key);
    }

    @Transactional
    public void setAndThrowError(String key, Object value) {
        customRedisTemplate.opsForValue().set(key+"1", value);
        customRedisTemplate.opsForValue().set(key+"2", value);
        throw new RuntimeException("error");
    }

    @Transactional
    public void incrTwoKeys(String key1, String key2) {
        customRedisTemplate.opsForValue().increment(key1, 1);
        customRedisTemplate.opsForValue().increment(key2, 1);
    }

    @Transactional
    public Object transactionalSetAndGet(String key, String value) {
        customRedisTemplate.opsForValue().set(key, value);
        return customRedisTemplate.opsForValue().get(key);
    }

    @Transactional
    public Object transactionalSetAndGetWithDifferentKeys(String key1, String value1, String key2) {
        customRedisTemplate.opsForValue().set(key1, value1);
        return customRedisTemplate.opsForValue().get(key2);
    }
}

package com.example.springplayground.redis;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@Transactional
@RequiredArgsConstructor
public class TransactionalRedis {
    private final RedisTemplate<String, Object> customRedisTemplate;

    public void set(String key, Object value) {
        customRedisTemplate.opsForValue().set(key, value);
    }

    public Object get(String key) {
        return customRedisTemplate.opsForValue().get(key);
    }

    public Object setThrowError(String key, Object value) {
        customRedisTemplate.opsForValue().set(key, value);
        throw new RuntimeException("error");
    }


}

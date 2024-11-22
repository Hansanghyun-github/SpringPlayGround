package com.example.springplayground.redis;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.time.Duration;

@Configuration
@EnableCaching
@Profile("redis")
public class RedisConfig {
    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(redisConnectionFactory);
        template.setKeySerializer(new StringRedisSerializer());
        template.setValueSerializer(new GenericJackson2JsonRedisSerializer());
        template.setHashKeySerializer(new StringRedisSerializer());
        template.setHashValueSerializer(new StringRedisSerializer());
        return template;
    }

    @Bean
    public MyStartupListener myStartupListener(CacheManager cacheManager) {
        return new MyStartupListener(cacheManager);
    }

    @Bean
    public TransactionalRedis transactionalRedis(RedisTemplate<String, Object> customRedisTemplate) {
        return new TransactionalRedis(customRedisTemplate);
    }

    @Bean
    public CachingService cachingService() {
        return new CachingService();
    }

    @Bean
    public RedisService redisService(RedisTemplate<String, Object> redisTemplate) {
        return new RedisService(redisTemplate);
    }
}

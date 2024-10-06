package com.example.springplayground.redis;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Import(EmbeddedRedisConfig.class)
public class RedisStringSerializerTest {
    @Autowired
    RedisTemplate<String, Object> customRedisTemplate;
    @Test
    void StringSerializer는_incr_연산이_가능하다() throws Exception {
        // given
        customRedisTemplate.opsForValue().set("key", "1");
        
        // when
        customRedisTemplate.opsForValue().increment("key");
        
        // then
        assertThat(customRedisTemplate.opsForValue().get("key")).isEqualTo("2");
    }

    @TestConfiguration
    static class StringSerializerConfig {
        @Bean(name = "customRedisTemplate")
        @Primary
        public RedisTemplate<String, Object> customRedisTemplate(RedisConnectionFactory redisConnectionFactory) {
            RedisTemplate<String, Object> template = new RedisTemplate<>();
            template.setConnectionFactory(redisConnectionFactory);
            template.setKeySerializer(new StringRedisSerializer());
            template.setValueSerializer(new StringRedisSerializer());
            return template;
        }
    }
}

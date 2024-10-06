package com.example.springplayground.redis;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.RedisSystemException;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@SpringBootTest
@Import(EmbeddedRedisConfig.class)
public class RedisJsonSerializerTest {
    @Autowired
    RedisTemplate<String, Object> customRedisTemplate;
    @Test
    void GenericJacksonSerializer는_incr_연산이_불가능하다() throws Exception {
        // given
        RedisSerializer<?> valueSerializer = customRedisTemplate.getValueSerializer();
        customRedisTemplate.opsForValue().set("key", "1");

        // when
        Assertions.assertThatThrownBy(() -> customRedisTemplate.opsForValue().increment("key"))
                .isInstanceOf(RedisSystemException.class);
    }

    @TestConfiguration
    static class StringSerializerConfig {
        @Bean(name = "customRedisTemplate")
        @Primary
        public RedisTemplate<String, Object> customRedisTemplate(RedisConnectionFactory redisConnectionFactory) {
            RedisTemplate<String, Object> template = new RedisTemplate<>();
            template.setConnectionFactory(redisConnectionFactory);
            template.setKeySerializer(new StringRedisSerializer());
            template.setValueSerializer(new GenericJackson2JsonRedisSerializer());
            return template;
        }
    }
}

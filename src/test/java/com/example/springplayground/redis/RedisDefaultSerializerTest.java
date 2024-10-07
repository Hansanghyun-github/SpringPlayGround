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
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Import(EmbeddedRedisConfig.class)
public class RedisDefaultSerializerTest {
    @Autowired
    RedisTemplate<String, Object> customRedisTemplate;
    @Test
    void DefaultSerializer는_incr_연산이_불가능하다() throws Exception {
        // given
        customRedisTemplate.opsForValue().set("key", "1");

        // when
        Assertions.assertThatThrownBy(() -> customRedisTemplate.opsForValue().increment("key"))
                .isInstanceOf(RedisSystemException.class);
    }

    @Test
    void DefaultSerializer는_변수의_타입을_유지한다() throws Exception {
        // given
        Long l = 1L;
        Integer i = 1;

        // when
        customRedisTemplate.opsForValue().set("key1", l);
        customRedisTemplate.opsForValue().set("key2", i);

        // then
        Object o1 = customRedisTemplate.opsForValue().get("key1");
        assertThat(l.equals(o1)).isTrue();
        assertThat(o1).isInstanceOf(Long.class);

        Object o2 = customRedisTemplate.opsForValue().get("key2");
        assertThat(i.equals(o2)).isTrue();
        assertThat(o2).isInstanceOf(Integer.class);
    }

    @TestConfiguration
    static class StringSerializerConfig {
        @Bean(name = "customRedisTemplate")
        @Primary
        public RedisTemplate<String, Object> customRedisTemplate(RedisConnectionFactory redisConnectionFactory) {
            RedisTemplate<String, Object> template = new RedisTemplate<>();
            template.setConnectionFactory(redisConnectionFactory);
            template.setKeySerializer(new StringRedisSerializer());
            template.setValueSerializer(new JdkSerializationRedisSerializer()); // default serializer
            return template;
        }
    }
}

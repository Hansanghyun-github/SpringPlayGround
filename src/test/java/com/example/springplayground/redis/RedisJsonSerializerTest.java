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

import static org.assertj.core.api.Assertions.*;

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
        assertThatThrownBy(() -> customRedisTemplate.opsForValue().increment("key"))
                .isInstanceOf(RedisSystemException.class);
    }

    @Test
    void GenericJacksonSerializer는_변수의_타입을_유지하지_않는다() throws Exception {
        // given
        Long l = 1L;

        // when
        customRedisTemplate.opsForValue().set("key", l);

        // then
        Object o = customRedisTemplate.opsForValue().get("key");
        assertThat(l.equals(o)).isFalse();
        assertThat(o).isInstanceOf(Integer.class);
    }

    @Test
    void GenericJacksonSerializer는_숫자의_크기에_따라서_타입이_정해진다() throws Exception {
        // given
        Long l1 = 1L;
        Long l2 = Long.MAX_VALUE;
        Integer i = 1;

        // when
        customRedisTemplate.opsForValue().set("key1", l1);
        customRedisTemplate.opsForValue().set("key2", l2);
        customRedisTemplate.opsForValue().set("key3", i);

        // then
        Object o1 = customRedisTemplate.opsForValue().get("key1");
        Object o2 = customRedisTemplate.opsForValue().get("key2");
        assertThat(o1).isInstanceOf(Integer.class);
        assertThat(o2).isInstanceOf(Long.class);

        Object o3 = customRedisTemplate.opsForValue().get("key3");
        assertThat(o3).isInstanceOf(Integer.class);
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

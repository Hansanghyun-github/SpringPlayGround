package com.example.springplayground.redis;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Import(EmbeddedRedisConfig.class)
public class RedisTransactionTest {
    @Autowired
    TransactionalRedis transactionalRedis;
    @Autowired
    RedisTemplate<String, Object> customRedisTemplate;

    @Test
    void 트랜잭션이_적용되어_롤백이_된다() throws Exception {
        // when
        try{
            transactionalRedis.setThrowError("key", "value");
        } catch (Exception e) {
            System.out.println(e);
        }

        // then
        Object o = customRedisTemplate.opsForValue().get("key");
        assertThat(o).isNull();
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
            template.setEnableTransactionSupport(true);
            return template;
        }
    }
}

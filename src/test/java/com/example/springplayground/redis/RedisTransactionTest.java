package com.example.springplayground.redis;

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
    void 에러가_발생하면_해당_명령이_취소된다() throws Exception {
        // when
        try{
            transactionalRedis.setAndThrowError("key", "value");
        } catch (Exception e) {
            System.out.println(e);
        }

        // then
        Object o = customRedisTemplate.opsForValue().get("key1");
        assertThat(o).isNull();

        o = customRedisTemplate.opsForValue().get("key2");
        assertThat(o).isNull();
    }

    @Test
    void 다른_트랜잭션은_따로_실행된다() throws Exception {
        // when
        transactionalRedis.set("key", "value");
        try{
            transactionalRedis.setAndThrowError("key", "value");
        } catch (Exception e) {
            System.out.println(e);
        }

        // then
        Object o = customRedisTemplate.opsForValue().get("key1");
        assertThat(o).isNull();

        o = customRedisTemplate.opsForValue().get("key2");
        assertThat(o).isNull();

        o = customRedisTemplate.opsForValue().get("key");
        assertThat(o).isEqualTo("value");
    }

    @Test
    void 특정_명령이_실패해도_다른_명령은_실행된다() throws Exception {
        // given
        customRedisTemplate.opsForHash().put("key1", "field", "value");
        customRedisTemplate.opsForValue().set("key2", "15");

        // when
        transactionalRedis.incrTwoKeys("key1", "key2"); // key1 는 hash 이므로 incr 이 불가능

        // then
        Object o = customRedisTemplate.opsForValue().get("key2");
        assertThat(o).isEqualTo("16");
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

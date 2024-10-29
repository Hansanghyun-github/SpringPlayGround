package com.example.springplayground.redis;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Primary;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SessionCallback;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Import(EmbeddedRedisConfig.class)
public class RedisTransactionTest {
    @Autowired
    TransactionalRedis transactionalRedis;
    @Autowired
    RedisTemplate<String, Object> customRedisTemplate;

    @AfterEach
    void tearDown() {
        customRedisTemplate.delete("key1");
        customRedisTemplate.delete("key2");
        customRedisTemplate.delete("key");
    }

    @Test
    void 트랜잭션_내부에서_에러가_발생하면_해당_명령이_취소된다() throws Exception {
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
    void 트랜잭션이_다른_명령들은_따로_실행된다() throws Exception {
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

    @Test
    void 트랜잭션_내에서_동일_키에_대한_set과_get이_동시에_실행하면_이전값과_새값이_다르게_반환된다() throws Exception {
        // given
        String oldValue = "old value";
        customRedisTemplate.opsForValue().set("key", oldValue);

        // when
        String newValue = "new value";
        Object oldO = transactionalRedis.transactionalSetAndGet("key", newValue);

        // then
        assertThat(oldO).isEqualTo(oldValue);
        Object newO = customRedisTemplate.opsForValue().get("key");
        assertThat(newO).isNotEqualTo(oldO);
        assertThat(newO).isEqualTo(newValue);
        System.out.println(oldO);
        System.out.println(newO);
    }

    @Test
    void 트랜잭션_안에서_에러_발생시_롤백된다() throws Exception {
        // given
        customRedisTemplate.opsForHash().put("key1", "field", "value1");

        // when
        Object o = null;
        try {
            o = transactionalRedis.transactionalSetAndGetWithDifferentKeys("key2", "value2", "key1");
        } catch (Exception e) {
            System.out.println(e);
        }

        // then
        assertThat(o).isNull();
        Object o1 = customRedisTemplate.opsForValue().get("key2");
        assertThat(o1).isNull();
    }

    @Test
    @Disabled
    void test_transaction() throws Exception {
        // given
        customRedisTemplate.opsForValue().set("key1", "value1");

        // when
        customRedisTemplate.execute(new SessionCallback<Object>() {
            @Override
            public <K, V> Object execute(RedisOperations<K, V> operations) throws DataAccessException {
                operations.multi();
                System.out.println(operations.opsForValue().get("key1"));
                operations.opsForValue().set((K) "key1", (V) "new value");
                return operations.exec();
            }
        });

        // then
        System.out.println(customRedisTemplate.opsForValue().get("key1"));
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

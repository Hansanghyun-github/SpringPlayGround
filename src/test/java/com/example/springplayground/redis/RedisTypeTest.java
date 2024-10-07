package com.example.springplayground.redis;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Import(EmbeddedRedisConfig.class)
public class RedisTypeTest {
    @Autowired RedisTemplate<String, Object> redisTemplate;

    @Test
    void Integer_and_Long_are_not_equal_because_of_type() throws Exception {
        // when
        Integer i = 1;
        Long l = 1L;

        // then
        assertThat(i.equals(l)).isFalse();
    }

    @Test
    void redis_value_is_determined_by_range_long_or_integer() throws Exception {
        // given
        Long l1 = 1L;
        redisTemplate.opsForValue().set("key1", l1);
        Long l2 = Long.MAX_VALUE;
        redisTemplate.opsForValue().set("key2", l2);

        // when
        Object o1 = redisTemplate.opsForValue().get("key1");
        Object o2 = redisTemplate.opsForValue().get("key2");

        // then
        assertThat(l1.equals(o1)).isFalse();
        assertThat(o1).isInstanceOf(Integer.class);

        assertThat(l2.equals(o2)).isTrue();
        assertThat(o2).isInstanceOf(Long.class);
    }
}

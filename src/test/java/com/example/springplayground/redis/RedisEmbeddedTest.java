package com.example.springplayground.redis;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.redis.core.RedisTemplate;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Import(EmbeddedRedisConfig.class)
class RedisEmbeddedTest {
    @Autowired
    RedisTemplate<String, Object> redisTemplate;

    @Test
    void 임베디드_레디스를_이용해_테스트를_진행한다() throws Exception {
        // when
        redisTemplate.opsForValue().set("key", "value");

        // then
        assertThat(redisTemplate.opsForValue().get("key")).isEqualTo("value");
    }

}
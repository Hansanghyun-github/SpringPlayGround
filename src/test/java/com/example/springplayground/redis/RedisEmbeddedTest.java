package com.example.springplayground.redis;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.Map;

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

    @Test
    void 없는_데이터에_get_요청을하면_null이_반환된다() throws Exception {
        // when
        Object o = redisTemplate.opsForValue().get("key");
        Object o1 = redisTemplate.opsForHash().get("key1", "field");

        // then
        assertThat(o).isNull();
        assertThat(o1).isNull();
    }

    @Test
    void 없는_데이터에_delete_요청을_해도_에러가_발생하지_않는다() throws Exception {
        // when
        Boolean delete = redisTemplate.delete("key");
        Long deleted = redisTemplate.opsForHash().delete("key1", "field");

        // then
        assertThat(delete).isFalse();
        assertThat(deleted).isEqualTo(0L);
    }

}
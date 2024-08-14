package com.example.springplayground.connectionPool;

import com.example.springplayground.util.LocalConfigFileUtils;
import com.example.springplayground.util.TestHttpUtils;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.net.http.HttpResponse;
import java.util.concurrent.atomic.AtomicInteger;

import static org.assertj.core.api.Assertions.assertThat;

class CheckConnectionPoolControllerTest {
    static AtomicInteger count = new AtomicInteger(0);
    @Test
    void test() throws Exception {
        // before test, have to run SpringPlayGroundApplication

        int expected = (int) LocalConfigFileUtils.getProperty("server.tomcat.max-connections") +
                (int) LocalConfigFileUtils.getProperty("server.tomcat.accept-count");
        assert expected > 0;

        final var NUMBER_OF_THREAD = 10;
        var threads = new Thread[NUMBER_OF_THREAD];

        for (int i = 0; i < NUMBER_OF_THREAD; i++) {
            threads[i] = new Thread(() -> incrementIfOk(TestHttpUtils.send("/test")));
        }

        for (final var thread : threads) {
            thread.start();
        }

        for (final var thread : threads) {
            thread.join();
        }
        assertThat(count.intValue()).isEqualTo(Math.min(NUMBER_OF_THREAD, expected));
    }

    private static void incrementIfOk(final HttpResponse<String> response) {
        if (response.statusCode() == 200) {
            count.incrementAndGet();
        }
    }

    private static void printResponseCode(final HttpResponse<String> response) {
        System.out.println(response.statusCode());
    }

}
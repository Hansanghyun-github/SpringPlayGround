package com.example.springplayground.connectionPool;

import com.example.springplayground.util.LocalConfigFileUtils;
import com.example.springplayground.util.TestHttpUtils;
import org.junit.jupiter.api.Test;

import java.net.http.HttpResponse;
import java.nio.channels.ClosedChannelException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicInteger;

import static org.assertj.core.api.Assertions.assertThat;

class CheckConnectionPoolControllerTest {
    static AtomicInteger count = new AtomicInteger(0);
    @Test
    void test() throws Exception {
        // before test, have to run SpringPlayGroundApplication
        // given
        final var NUMBER_OF_THREAD = 10;
        ExecutorService service = Executors.newFixedThreadPool(NUMBER_OF_THREAD);
        Future<?>[] futures = new Future[NUMBER_OF_THREAD];

        // when
        for (int i = 0; i < NUMBER_OF_THREAD; i++) {
            futures[i] = service.submit(() -> incrementIfOk(TestHttpUtils.send("/test")));
        }
        for (int i = 0; i < NUMBER_OF_THREAD; i++) {
            try {
                futures[i].get();
            } catch (Exception e) {
                Throwable cause = e.getCause();
                while(cause.getCause() != null && cause.getCause() != cause) {
                    cause = cause.getCause();
                }
                assertThat(cause).isInstanceOf(ClosedChannelException.class);
            }
        }

        // then
        int expected = getExpectedConnectionsCount();
        assertThat(count.intValue()).isEqualTo(Math.min(NUMBER_OF_THREAD, expected));
        service.shutdown();
    }

    private static int getExpectedConnectionsCount() {
        return (int) LocalConfigFileUtils.getProperty("server.tomcat.max-connections") +
                (int) LocalConfigFileUtils.getProperty("server.tomcat.accept-count");
    }

    private static void incrementIfOk(final HttpResponse<String> response) {
        if (response.statusCode() == 200) {
            count.incrementAndGet();
        }
    }

}
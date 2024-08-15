package com.example.springplayground.connectionPool;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantLock;

@RestController
@Slf4j
@RequiredArgsConstructor
public class CheckConnectionPoolController {

    private AtomicInteger n = new AtomicInteger(0);

    @GetMapping("/test")
    public void getConnectionPoolInfo() throws InterruptedException {
        int i = n.incrementAndGet();
        log.info("Connection pool test + " + i);
        Thread.sleep(5000);
        log.info("Connection pool test end - " + i);
    }

}

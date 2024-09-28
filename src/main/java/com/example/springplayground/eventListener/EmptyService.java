package com.example.springplayground.eventListener;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class EmptyService {
    private final ApplicationEventPublisher applicationEventPublisher;

    public void createEvent(Integer data) {
        log.info("create event");
        applicationEventPublisher.publishEvent(new MyEvent1(data));
    }

    public void slowEvent() throws InterruptedException {
        log.info("slow event: sleep 1s");
        Thread.sleep(1000);
        applicationEventPublisher.publishEvent(new MyEvent2());
    }
    public void errorEvent(Integer data) {
        applicationEventPublisher.publishEvent(new MyEvent1(data));
        throw new RuntimeException("error");
    }

}

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

    public void createEvent1(Integer data) {
        log.info("create event");
        applicationEventPublisher.publishEvent(new MyEvent1(data));
    }

    public void createEvent2() throws InterruptedException {
        log.info("create event2");
        Thread.sleep(1000);
        applicationEventPublisher.publishEvent(new MyEvent2());
    }

}

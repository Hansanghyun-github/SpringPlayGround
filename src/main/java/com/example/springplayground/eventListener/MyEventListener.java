package com.example.springplayground.eventListener;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
@RequiredArgsConstructor
@Slf4j
public class MyEventListener {

    @TransactionalEventListener
    public void eventListenerMethod(MyEvent1 myEvent1) {
        log.info("event data: {}", myEvent1.getData());
    }

    @TransactionalEventListener
    public void slowEventListenerMethod(MyEvent2 myEvent2) throws InterruptedException {
        Thread.sleep(1000);
        log.info("event2");
    }

}

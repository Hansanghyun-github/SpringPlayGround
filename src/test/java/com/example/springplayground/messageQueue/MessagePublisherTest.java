package com.example.springplayground.messageQueue;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("messageQueue")
class MessagePublisherTest {
    @Autowired MessagePublisher messagePublisher;

    @Test
    void fsdf() throws Exception {
        // given

        // when
        messagePublisher.sendMessage("Hello, World!");

        // then
    }

}
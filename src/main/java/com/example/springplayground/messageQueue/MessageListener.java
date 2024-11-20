package com.example.springplayground.messageQueue;

import org.springframework.amqp.rabbit.annotation.RabbitListener;

public class MessageListener {
    @RabbitListener(queues = "myQueue")
    public void receiveMessage(String message) {
        System.out.println("Received message: " + message);
    }
}

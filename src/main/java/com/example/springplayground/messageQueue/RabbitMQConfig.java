package com.example.springplayground.messageQueue;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("messageQueue")
public class RabbitMQConfig {

    @Bean
    public MessageListener messageListener() {
        return new MessageListener();
    }

    @Bean
    public MessagePublisher messagePublisher(RabbitTemplate rabbitTemplate) {
        return new MessagePublisher(rabbitTemplate);
    }
}

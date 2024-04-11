package com.inexture.config.queue;

import com.gbs.common.constants.RabbitMqConstants;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class NotificationQueue {
    // Define Queue
    @Bean
    Queue saveUser() {
        return new Queue(RabbitMqConstants.USER_QUEUE, true);
    }

    // Define Exchange
    @Bean
    DirectExchange directExchange() {
        return new DirectExchange(RabbitMqConstants.DIRECT_EXCHANGE);
    }

    // Queue binding
    @Bean
    Binding saveUserBinding(Queue saveUser, DirectExchange directExchange) {
        return BindingBuilder.bind(saveUser).to(directExchange).with(RabbitMqConstants.USER_ROUTING_KEY);
    }
}

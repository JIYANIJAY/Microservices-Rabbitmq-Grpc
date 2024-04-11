package com.gbs.common.config.rabbitmq;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.amqp.support.converter.SimpleMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class RabbitMqConfig {


//    public static final String REPLY_EXCHANGE = "reply_exchange";
//
//
//    @Bean
//    public static DirectExchange exchange() {
//        return new DirectExchange(REPLY_EXCHANGE);
//    }
//
//    @Bean
//    Queue replyQueue() {
//        return new Queue(REPLY_EXCHANGE);
//    }

//    @Bean
//    Binding replyBinding() {
//        return BindingBuilder.bind(replyQueue()).to(exchange()).with(REPLY_EXCHANGE);
//    }

    @Bean
    public Jackson2JsonMessageConverter producerJackson2MessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate template = new RabbitTemplate(connectionFactory);
        template.setMessageConverter(producerJackson2MessageConverter());
//        template.setReplyAddress(REPLY_EXCHANGE);
        template.setReplyTimeout(6000);
        return template;
    }

}

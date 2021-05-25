package com.gao.rabbitmq.topic;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.UUID;


@Configuration
public class RabbitConfig {

    public static final String DEFAULT_TOPIC_EXCHANGE = "prontera.topic";

    public static final String TOPIC_QUEUE = "p-" + UUID.randomUUID();
    public static final String TOPIC_ROUTE_KEY = "#.#"; // 下表有例子

    @Bean
    public MessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public TopicExchange topicExchange() {
        return new TopicExchange(DEFAULT_TOPIC_EXCHANGE, true, true);
    }

    /*
    以下配置只有消费端需要
     */
    @Bean
    public Queue randomQueue() {
        return new Queue(TOPIC_QUEUE, true, false, true);
    }

    @Bean
    public Binding topicBinding() {
        return BindingBuilder.bind(randomQueue()).to(topicExchange()).with(TOPIC_ROUTE_KEY);
    }
}
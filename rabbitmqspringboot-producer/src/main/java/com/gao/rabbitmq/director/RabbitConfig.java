package com.gao.rabbitmq.director;

import org.springframework.amqp.core.*;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class RabbitConfig {

    public static final String DEFAULT_DIRECT_EXCHANGE = "direct.exchange";
    public static final String TRADE_QUUE = "direct.queue";
    public static final String TRADE_ROUTE_KEY = "direct.queue";

    @Bean
    public MessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public DirectExchange pronteraExchange() {
        return new DirectExchange(DEFAULT_DIRECT_EXCHANGE, true, true);
    }
}
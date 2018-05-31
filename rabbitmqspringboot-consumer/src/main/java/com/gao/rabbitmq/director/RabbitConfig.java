package com.gao.rabbitmq.director;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
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
    public DirectExchange pronteraExchange() {
        return new DirectExchange(DEFAULT_DIRECT_EXCHANGE, true, true);
    }

    @Bean
    public Queue tradeQueue() {
        return new Queue(TRADE_QUUE, true, false, true);
    }

    @Bean
    public Binding tradeBinding() {
        return BindingBuilder.bind(tradeQueue()).to(pronteraExchange()).with(TRADE_ROUTE_KEY);
    }

    @Bean
    public MessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }
}
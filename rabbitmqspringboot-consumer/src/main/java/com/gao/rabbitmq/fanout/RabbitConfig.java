package com.gao.rabbitmq.fanout;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.UUID;


@Configuration
public class RabbitConfig {

    public static final String DEFAULT_FANOUT_EXCHANGE = "fanout.exchange";
    public static final String FANOUT_QUEUE = "p-" + UUID.randomUUID();

    @Bean
    public MessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public FanoutExchange fanoutExchange() {
        return new FanoutExchange(DEFAULT_FANOUT_EXCHANGE, true, true);
    }

    /**
     * 以下两个配置只需要在消费端配置
     * @return
     */
    @Bean
    public Queue randomQueue() {
        return new Queue(FANOUT_QUEUE, true, false, true);
    }

    @Bean
    public Binding fanoutBinding() {
        return BindingBuilder.bind(randomQueue()).to(fanoutExchange());
    }

}
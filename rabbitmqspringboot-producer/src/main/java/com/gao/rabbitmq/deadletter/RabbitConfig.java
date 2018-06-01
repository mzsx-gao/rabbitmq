package com.gao.rabbitmq.deadletter;

import org.springframework.amqp.core.*;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;


@Configuration
public class RabbitConfig {

    //死信交换机
    @Bean
    public DirectExchange deadLetterExchange() {
        return new DirectExchange("dead-letter-exchange", true, true);
    }
    //死信队列
    @Bean
    public Queue deadQueue() {
        return new Queue("dead_queue",true,false,false);
    }

    //绑定死信交换机与死信队列
    @Bean
    public Binding deadBinding() {
        return BindingBuilder.bind(deadQueue()).to(deadLetterExchange()).with("channel_queue");
    }


    @Bean
    public DirectExchange channelExchange() {
        return new DirectExchange("channel_exchange", true, true);
    }
    @Bean
    public Queue channelQueue() {
        Map<String, Object> args = new HashMap<>(2);
        args.put("x-dead-letter-exchange", "dead-letter-exchange");//声明死信交换机
        return new Queue("channel_queue",true,false,false,args);
    }
    @Bean
    public Binding deadLetterBinding() {
        return BindingBuilder.bind(channelQueue()).to(channelExchange()).with("channel_queue");

    }



}
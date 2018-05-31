package com.gao.rabbitmq.publishConfirm;

import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RabbitMQListener {

    @Autowired
    public ConnectionFactory connectionFactory;

    @RabbitListener(queues = "dev.channel_fenrun",containerFactory = "rabbitListenerContainerFactory")
    public void handlerMsg(String message){
        System.out.println(System.currentTimeMillis()+"消费消息: " + message);
    }
}
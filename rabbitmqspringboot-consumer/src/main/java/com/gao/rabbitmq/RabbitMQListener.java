package com.gao.rabbitmq;

import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class RabbitMQListener {

    @Autowired
    public ConnectionFactory connectionFactory;

    @RabbitListener(containerFactory = "rabbitListenerContainerFactory",
            bindings = @QueueBinding(value = @Queue(value = "spring-boot-queue", durable = "true"),
                    exchange = @Exchange(value = "spring-boot-exchange", type = ExchangeTypes.DIRECT,durable = "true"),
                    key = "spring-boot-routingKey"),
                    admin = "rabbitAdmin")
    public void handlerMsg(String message){

        System.out.println(System.currentTimeMillis()+"消费消息: " + message);
    }




}
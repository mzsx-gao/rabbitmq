package com.gao.rabbitmq;

import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


public class RabbitmqFactory {

    public static ConnectionFactory connectionFactory() {
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory();
        connectionFactory.setAddresses("172.16.6.51:9673");
        connectionFactory.setUsername("xdt");
        connectionFactory.setPassword("xdt");
        connectionFactory.setVirtualHost("/loan");
        connectionFactory.setPublisherConfirms(true);
        return connectionFactory;
    }
}
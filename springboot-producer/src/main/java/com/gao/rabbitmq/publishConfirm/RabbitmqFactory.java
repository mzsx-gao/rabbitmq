package com.gao.rabbitmq.publishConfirm;

import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;


public class RabbitmqFactory {

    public static ConnectionFactory connectionFactory() {
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory();
        connectionFactory.setHost("172.16.6.51");
        connectionFactory.setPort(9673);
        connectionFactory.setUsername("xdt");
        connectionFactory.setPassword("xdt");
        connectionFactory.setVirtualHost("/mqtest");
        connectionFactory.setPublisherConfirms(true);
        return connectionFactory;
    }
}
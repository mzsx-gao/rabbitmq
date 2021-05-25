package com.gao.rabbitmq;

import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;


@Configuration
@EnableAutoConfiguration
@ComponentScan(basePackages = {"com.gao.rabbitmq.director"})
//@ComponentScan(basePackages = {"com.gao.rabbitmq.fanout"})
//@ComponentScan(basePackages = {"com.gao.rabbitmq.topic"})
@ServletComponentScan
public class StartupConsumer {
    public static void main(String[] args) {
        SpringApplication.run(StartupConsumer.class,args);
    }
}

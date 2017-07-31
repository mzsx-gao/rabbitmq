package com.gao.rabbitmq;

import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;


@Configuration
@EnableAutoConfiguration
@ComponentScan
@ServletComponentScan
@EnableRabbit
public class StartupConsumer {
    public static void main(String[] args) {
        SpringApplication.run(StartupConsumer.class,args);
    }
}

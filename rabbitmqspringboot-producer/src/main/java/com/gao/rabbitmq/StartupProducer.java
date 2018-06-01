package com.gao.rabbitmq;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;


@Configuration
@EnableAutoConfiguration
//@ComponentScan(basePackages = {"com.gao.rabbitmq.director"})
//@ComponentScan(basePackages = {"com.gao.rabbitmq.fanout"})
//@ComponentScan(basePackages = {"com.gao.rabbitmq.topic"})
@ComponentScan(basePackages = {"com.gao.rabbitmq.deadletter2"})
@ServletComponentScan
public class StartupProducer {
    public static void main(String[] args) {
        SpringApplication.run(StartupProducer.class,args);
    }
}

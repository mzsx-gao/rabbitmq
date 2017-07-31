package com.gao.rabbitmq;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;


@Configuration
@EnableAutoConfiguration
@ComponentScan
@ServletComponentScan
public class StartupProducer {
    public static void main(String[] args) {
        SpringApplication.run(StartupProducer.class,args);
    }
}

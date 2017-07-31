package com.gao.rabbitmq;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import java.util.Date;


@Configuration
public class ConsumerConfig {

    public static final String EXCHANGE   = "spring-boot-exchange";
    public static final String ROUTINGKEY = "spring-boot-routingKey";


    @Autowired
    public ConnectionFactory connectionFactory;



    /**
     * 针对消费者配置
     * 1. 设置交换机类型
     * 2. 将队列绑定到交换机
     FanoutExchange: 将消息分发到所有的绑定队列，无routingkey的概念
     HeadersExchange ：通过添加属性key-value匹配
     DirectExchange:按照routingkey分发到指定队列
     TopicExchange:多关键字匹配
     */
    @Bean
    public DirectExchange defaultExchange() {
        DirectExchange directExchange = new DirectExchange(EXCHANGE,true,false);
        rabbitAdmin().declareExchange(directExchange);
        return directExchange;
    }

    @Bean
    public Queue queue() {
        Queue queue = new Queue("spring-boot-queue", true,false,false,null); //队列持久
        rabbitAdmin().declareQueue(queue);
        return queue;

    }

    @Bean
    public Binding binding() {
        Binding binding =BindingBuilder.bind(queue()).to(defaultExchange()).with(ConsumerConfig.ROUTINGKEY);
        rabbitAdmin().declareBinding(binding);
        return binding;
    }

    @Bean
    public RabbitAdmin rabbitAdmin(){
        RabbitAdmin admin = new RabbitAdmin(connectionFactory);
        admin.setAutoStartup(true);
        return admin;
    }

    @Bean
    public ConnectionFactory connectionFactory(@Value("${mq.addresses}") String serverAddresses,
                                               @Value("${mq.username}") String msgUserName,
                                               @Value("${mq.password}") String msgPassword){
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory();
        connectionFactory.setAddresses(serverAddresses);
        connectionFactory.setUsername(msgUserName);
        connectionFactory.setPassword(msgPassword);
        connectionFactory.setPublisherConfirms(true);
        connectionFactory.setVirtualHost("/");
        return connectionFactory;
    }

    @Bean
    public SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory(ConnectionFactory connectionFactory) {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
        factory.setMessageConverter(new Jackson2JsonMessageConverter());
        factory.setConcurrentConsumers(2);
        factory.setMaxConcurrentConsumers(10);
        return factory;
    }
}
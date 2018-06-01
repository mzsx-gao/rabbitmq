package com.gao.rabbitmq.deadletter;

/**
 *   名称: HelloReceiver1.java
 *   描述:
 *   类型: JAVA
 *   最近修改时间:2018/5/30 15:37
 *   @version [版本号, V1.0]
 *   @since 2018/5/30 15:37
 *   @author gaoshudian
 */

import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class DeadletterReceiver {

//    @RabbitListener(queues = {"dead_queue"})
//    public void redirect(Message message, Channel channel) throws IOException {
//        channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
//        System.out.println("5s后消费:"+new String (message.getBody()));
//    }

    @RabbitListener(queues = {"dead_queue"})
    public void redirect(String message,Channel channel) throws IOException {
        System.out.println("5s后消费:"+message);
    }

}
package com.gao.rabbitmq.rejectmsg;

import com.gao.rabbitmq.RabbitMQUtil;
import com.rabbitmq.client.*;

import java.io.IOException;

/**
 * 普通的消费者
 */
public class NormalConsumerA {

    public static void main(String[] argv) throws Exception {
        Channel channel = RabbitMQUtil.getChannel();
        channel.exchangeDeclare("direct_logs", "direct");
        String queueName = "focuserror";
        channel.queueDeclare(queueName,false,false, false,null);
        String routekey = "error";
        channel.queueBind(queueName,"direct_logs",routekey);
        System.out.println("waiting for message........");

        /*声明了一个消费者*/
        final Consumer consumer = new DefaultConsumer(channel){
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope,
                                       AMQP.BasicProperties properties, byte[] body) throws IOException {
                String message = new String(body, "UTF-8");
                System.out.println("Received["+envelope.getRoutingKey() +"]"+message);
                //消息确认
                channel.basicAck(envelope.getDeliveryTag(),false);
            }
        };
        /*消费者正式开始在指定队列上消费消息*/
        channel.basicConsume(queueName,false,consumer);
    }
}

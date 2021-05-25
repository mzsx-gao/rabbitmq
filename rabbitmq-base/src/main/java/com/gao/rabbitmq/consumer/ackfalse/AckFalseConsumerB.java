package com.gao.rabbitmq.consumer.ackfalse;

import com.gao.rabbitmq.RabbitMQUtil;
import com.rabbitmq.client.*;

import java.io.IOException;

/**
 * 类说明：消息者对消息进行确认（手动确认）
 */
public class AckFalseConsumerB {

    public static void main(String[] argv) throws Exception {
        Channel channel = RabbitMQUtil.getChannel();
        channel.exchangeDeclare("direct_logs", "direct");
        /*声明一个队列*/
        String queueName = "focuserror";
        channel.queueDeclare(queueName, false, false, false, null);
        /*绑定，将队列和交换器通过路由键进行绑定*/
        String routekey = "error";/*表示只关注error级别的日志消息*/
        channel.queueBind(queueName, "direct_logs", routekey);
        System.out.println("waiting for message........");
        /*声明了一个消费者*/
        final Consumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope,
                                       AMQP.BasicProperties properties, byte[] body) throws IOException {
                String message = new String(body, "UTF-8");
                System.out.println("Received[" + envelope.getRoutingKey() + "]" + message);
                System.out.println("手动确认的tag:" + envelope.getDeliveryTag());
                channel.basicAck(envelope.getDeliveryTag(), false);
            }
        };
        /*消费者正式开始在指定队列上消费消息*/
        //这里第二个参数是自动确认参数，如果是false则是手动确认
        channel.basicConsume(queueName, false, consumer);
    }
}
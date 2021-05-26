package com.gao.rabbitmq.setQueue;

import com.gao.rabbitmq.RabbitMQUtil;
import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeoutException;

/**
 * 控制队列的参数
 */
public class SetQueueConsumer {
    public final static String EXCHANGE_NAME = "set_queue";

    public static void main(String[] argv) throws Exception {
        Channel channel = RabbitMQUtil.getChannel();
        channel.exchangeDeclare(EXCHANGE_NAME, "direct");

        /*自动过期队列--参数需要Map传递*/
        String queueName = "setQueue";
        Map<String, Object> arguments = new HashMap<>();
        arguments.put("x-expires",10*1000);//队列在超过一定时间没用，会被从rabbitmq中删除
        arguments.put("x-message-ttl",10*1000);//设置了该队列所有消息的存活时间，单位毫秒
        /*加入队列的各种参数*/
        channel.queueDeclare(queueName,true,true, false,arguments);

        /*绑定，将队列和交换器通过路由键进行绑定*/
        String routekey = "error";/*表示只关注error级别的日志消息*/
        channel.queueBind(queueName,EXCHANGE_NAME,routekey);

        System.out.println("waiting for message........");

        /*声明了一个消费者*/
        final Consumer consumer = new DefaultConsumer(channel){
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope,
                                       AMQP.BasicProperties properties, byte[] body) throws IOException {
                String message = new String(body, "UTF-8");
                System.out.println("Received["+envelope.getRoutingKey() +"]"+message);
            }
        };
        /*消费者正式开始在指定队列上消费消息*/
        channel.basicConsume(queueName,true,consumer);
    }
}
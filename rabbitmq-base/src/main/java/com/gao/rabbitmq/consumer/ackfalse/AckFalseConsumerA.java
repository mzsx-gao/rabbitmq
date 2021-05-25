package com.gao.rabbitmq.consumer.ackfalse;

import com.gao.rabbitmq.RabbitMQUtil;
import com.rabbitmq.client.*;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

/**
 * 类说明：消息者不对消息进行确认会怎样？
 * 通过运行程序，启动两个消费者 A、B，都可以收到消息，但是其中有一个消费者 A 不会对消息进行确认，当把这个消费者 A 关闭后，
 * 消费者 B 又会收到本来发送给消费者 A 的消息。所以我们一般使用手动确认的方法是，将消息的处理放在 try/catch 语句块中，
 * 成功处理了，就给 RabbitMQ 一个确认应答，如果处理异常了，就在 catch 中，进行消息的拒绝
 */
public class AckFalseConsumerA {

    public static void main(String[] argv) throws Exception {
        Channel channel = RabbitMQUtil.getChannel();
        channel.exchangeDeclare("direct_logs", "direct");

        /*声明一个队列*/
        String queueName = "focuserror";
        channel.queueDeclare(queueName,false,false, false,null);

        /*绑定，将队列和交换器通过路由键进行绑定*/
        String routekey = "error";/*表示只关注error级别的日志消息*/
        channel.queueBind(queueName,"direct_logs",routekey);
        System.out.println("waiting for message........");
        /*声明了一个消费者*/
        final Consumer consumer = new DefaultConsumer(channel){
            @Override
            public void handleDelivery(String consumerTag,Envelope envelope,
                                       AMQP.BasicProperties properties, byte[] body) throws IOException {
                try {
                    String message = new String(body, "UTF-8");
                    System.out.println("Received["+envelope.getRoutingKey() +"]"+message);
                     //TODO
                     //确认
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                    //拒绝
                }
            }
        };
        /*消费者正式开始在指定队列上消费消息*/
        //这里第二个参数是自动确认参数，如果是false则是手动确认
        channel.basicConsume(queueName,false,consumer);
    }
}
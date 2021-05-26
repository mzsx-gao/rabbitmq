package com.gao.rabbitmq.rejectmsg;

import com.gao.rabbitmq.RabbitMQUtil;
import com.rabbitmq.client.*;

import java.io.IOException;

/**
 * 拒绝消息的消费者
 * Reject 在拒绝消息时，可以使用 requeue 标识，告诉 RabbitMQ 是否需要重新发送给别的消费者。如果是 false 则不重新发送，
 * 一般这个消息就会被RabbitMQ 丢弃。Reject 一次只能拒绝一条消息。如果是 true 则消息发生了重新投递
 * Nack 跟 Reject 类似，只是它可以一次性拒绝多个消息。也可以使用 requeue 标识，这是 RabbitMQ 对 AMQP 规范的一个扩展
 */
public class RejectRequeuConsumer {

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
                try{
                    String message = new String(body, "UTF-8");
                    System.out.println("Received[" +envelope.getRoutingKey() +"]"+message);
                    throw new RuntimeException("处理异常"+message);
                }catch (Exception e){
                    e.printStackTrace();
                    //Reject方式拒绝(这里第2个参数决定是否重新投递)
//                    channel.basicReject(envelope.getDeliveryTag(),true);
                    //Nack方式的拒绝（第2个参数决定是否批量）
                    channel.basicNack(envelope.getDeliveryTag(), false, true);
                }
            }
        };
        /*消费者正式开始在指定队列上消费消息*/
        channel.basicConsume(queueName,false,consumer);
    }
}
package com.gao.rabbitmq.consumer.qos;

import com.gao.rabbitmq.RabbitMQUtil;
import com.rabbitmq.client.*;
import java.io.IOException;

/**
 * 类说明：普通的消费者
 */
public class QosConsumerMain {

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

        //方式一:单条确认
//        final Consumer consumer = new DefaultConsumer(channel){
//            @Override
//            public void handleDelivery(String consumerTag, Envelope envelope,
//                                       AMQP.BasicProperties properties, byte[] body) throws IOException {
//                String message = new String(body, "UTF-8");
//                System.out.println("Received["+envelope.getRoutingKey() +"]"+message);
//                //单条确认
//                channel.basicAck(envelope.getDeliveryTag(),false);
//            }
//        };
//        channel.basicConsume(queueName,false,consumer);

        //方式二:批量确认
        //如果是两个消费者(QOS ,批量)则轮询获取数据
        //150条预取(150都取出来 150， 210-150  60  )
        channel.basicQos(150,true);
        //自定义消费者批量确认
        BatchAckConsumer batchAckConsumer = new BatchAckConsumer(channel);
        channel.basicConsume(queueName,false,batchAckConsumer);
    }
}
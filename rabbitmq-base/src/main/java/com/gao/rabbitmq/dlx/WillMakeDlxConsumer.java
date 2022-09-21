package com.gao.rabbitmq.dlx;

import com.gao.rabbitmq.RabbitMQUtil;
import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeoutException;

/**
 * 普通的消费者,但是自己无法消费的消息，将投入死信队列
 * 消息变成死信一般是以下三种情况：
 * 1.消息被拒绝，并且设置 requeue 参数为 false
 * 2.消息过期（默认情况下 Rabbit 中的消息不过期，但是可以设置队列的过期时间和消息的过期时间以达到消息过期的效果）
 * 3.队列达到最大长度（一般当设置了最大队列长度或大小并达到最大值时）
 */
public class WillMakeDlxConsumer {

    public static void main(String[] argv) throws Exception {
        Channel channel = RabbitMQUtil.getChannel();
        channel.exchangeDeclare(DlxProducer.EXCHANGE_NAME, BuiltinExchangeType.TOPIC);

        /**
         * 声明死信交换器与死信路由键
         * 死信路由键，会替换消息原来的路由键
         */
        Map<String,Object> args = new HashMap<>();
        args.put("x-dead-letter-exchange", DlxProcessConsumer.DLX_EXCHANGE_NAME);
        args.put("x-dead-letter-routing-key", "deal");

        String queueName = "dlx_make";
        channel.queueDeclare(queueName,false,true, false, args);
        channel.queueBind(queueName, DlxProducer.EXCHANGE_NAME,"#");
        System.out.println("waiting for message........");
        /*声明了一个消费者*/
        final Consumer consumer = new DefaultConsumer(channel){
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope,
                                       AMQP.BasicProperties properties, byte[] body) throws IOException {
                String message = new String(body, "UTF-8");
                //如果是king的消息确认
                if(envelope.getRoutingKey().equals("king")){
                    System.out.println("Received[" +envelope.getRoutingKey() +"]"+message);
                    channel.basicAck(envelope.getDeliveryTag(), false);
                }else{
                    //如果是其他的消息拒绝（requeue=false），成为死信消息
                    System.out.println("Will reject[" +envelope.getRoutingKey() +"]"+message);
                    channel.basicReject(envelope.getDeliveryTag(), false);
                }

            }
        };
        channel.basicConsume(queueName,false,consumer);
    }
}
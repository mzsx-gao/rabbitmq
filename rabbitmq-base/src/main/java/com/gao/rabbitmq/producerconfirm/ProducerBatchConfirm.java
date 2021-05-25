package com.gao.rabbitmq.producerconfirm;

import com.gao.rabbitmq.RabbitMQUtil;
import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * 生产者——发送方确认模式-》批量确认
 * 使用同步方式等所有的消息发送之后才会执行后面代码，只要有一个消息未到达交换器就会抛出 IOException 异常
 */
public class ProducerBatchConfirm {

    public final static String EXCHANGE_NAME = "producer_wait_confirm";
    private final static String ROUTE_KEY = "king";

    public static void main(String[] args) throws Exception {
        Channel channel = RabbitMQUtil.getChannel();
        channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.DIRECT);
        // 添加失败通知监听器
        channel.addReturnListener(new ReturnListener() {
            public void handleReturn(int replyCode, String replyText,
                                     String exchange, String routingKey,
                                     AMQP.BasicProperties properties,
                                     byte[] body) throws IOException {
                String message = new String(body);
                System.out.println("消息发送失败【:" + message + "】,replyCode：" + replyCode + ",replyText:" + replyText
                    + ",exchange:" + exchange + ",routingKey:" + routingKey + ",message:" + message);
            }
        });
        // 启用发送者确认模式
        channel.confirmSelect();

        //所有日志严重性级别
        for (int i = 0; i < 10; i++) {
            // 发送的消息
            String message = "Hello World_" + (i + 1);
            //参数1：exchange name
            //参数2：routing key
            channel.basicPublish(EXCHANGE_NAME, ROUTE_KEY, true, null, message.getBytes());
            System.out.println(" Sent Message: [" + ROUTE_KEY + "]:'" + message + "'");
        }
        // 启用发送者确认模式（批量确认）
        channel.waitForConfirmsOrDie();
        // 关闭频道和连接
        channel.close();
        channel.getConnection().close();
    }
}
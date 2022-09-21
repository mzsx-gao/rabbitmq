package com.gao.rabbitmq.rejectmsg;

import com.gao.rabbitmq.RabbitMQUtil;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * 存放到延迟队列的元素，对业务数据进行了包装
 */
public class RejectProducer {

    public final static String EXCHANGE_NAME = "direct_logs";

    public static void main(String[] args) throws Exception {
        Channel channel = RabbitMQUtil.getChannel();
        channel.exchangeDeclare(EXCHANGE_NAME, "direct");
        //所有日志严重性级别
        for (int i = 0; i < 10; i++) {
            // 发送的消息
            String message = "Hello World_" + (i + 1);
            //参数1：exchange name
            //参数2：routing key
            channel.basicPublish(EXCHANGE_NAME, "error", null, message.getBytes());
            System.out.println(" [x] Sent 'error':'" + message + "'");
        }
        // 关闭频道和连接
        channel.close();
        channel.getConnection().close();
    }
}
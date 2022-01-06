package com.gao.rabbitmq.messageProperties.msgDurable;

import com.gao.rabbitmq.RabbitMQUtil;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.MessageProperties;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * 消息的持久化生产者
 * 默认情况下，队列和交换器在服务器重启后都会消失，消息当然也是。将队列和交换器的 durable 属性设为 true，缺省为 false，
 * 但是消息要持久化还不够，还需要将消息在发布前，将投递模式设置为 2。消息要持久化，必须要有持久化的队列、交换器和投递模式都为 2
 */
public class MsgAttrProducer {

    public final static String EXCHANGE_NAME = "msg_durable";

    public static void main(String[] args) throws Exception {
        /* 创建连接,连接到RabbitMQ*/
        Channel channel = RabbitMQUtil.getChannel();
        //创建持久化交换器 durable=true
        channel.exchangeDeclare(EXCHANGE_NAME,"direct",true);

        /*日志消息级别，作为路由键使用*/
        String[] routekeys = {"king","mark","james"};
        for(int i=0;i<3;i++){
            String routekey = routekeys[i%3];
            String msg = "Hellol,RabbitMq"+(i+1);
            //发布持久化的消息(delivery-mode=2)
            channel.basicPublish(EXCHANGE_NAME,routekey,
                    MessageProperties.PERSISTENT_TEXT_PLAIN, msg.getBytes());
        }
        channel.close();
        channel.getConnection().close();
    }
}
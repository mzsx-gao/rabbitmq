package com.gao.rabbitmq.dlx;

import com.gao.rabbitmq.RabbitMQUtil;
import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * 类说明：私信队列生产者
 */
public class DlxProducer {

    public final static String EXCHANGE_NAME = "dlx_make";

    public static void main(String[] args) throws Exception {
        Channel channel = RabbitMQUtil.getChannel();
        channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.TOPIC);

        /*日志消息级别，作为路由键使用*/
        String[] routekeys = {"king","mark","james"};
        for(int i=0;i<3;i++){
            String routekey = routekeys[i%3];
            String msg = "Hellol,RabbitMq"+(i+1);
            /*发布消息，需要参数：交换器，路由键，其中以日志消息级别为路由键*/
            channel.basicPublish(EXCHANGE_NAME,routekey,null, msg.getBytes());
            System.out.println("Sent "+routekey+":"+msg);
        }
        // 关闭频道和连接
        channel.close();
        channel.getConnection().close();
    }
}
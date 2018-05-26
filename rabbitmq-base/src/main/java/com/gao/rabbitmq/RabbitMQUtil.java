package com.gao.rabbitmq;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

/**
 *  * 名称: RabbitMQUtil.java <br>
 *  * 描述: <br>
 *  * 类型: JAVA <br>
 *  * 最近修改时间:2017/7/19 14:41.<br>
 *  * @version [版本号, V1.0]
 *  * @since 2017/7/19 14:41.
 *  * @author gaoshudian
 */
public class RabbitMQUtil {

    //获取rabbitMQ通讯的通道
    public static Channel getChannel()throws Exception{
        //创建连接工厂
        ConnectionFactory factory = new ConnectionFactory();
        //设置RabbitMQ相关信息
        factory.setHost("172.16.6.51");
        factory.setUsername("xdt");
        factory.setPassword("xdt");
        factory.setPort(9673);
        factory.setVirtualHost("/loan");
        //创建一个新的连接
        Connection connection = factory.newConnection();
        //创建一个通道
        Channel channel = connection.createChannel();
        return channel;
    }
}
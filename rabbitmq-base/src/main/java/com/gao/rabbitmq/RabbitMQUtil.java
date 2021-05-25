package com.gao.rabbitmq;

import com.rabbitmq.client.*;

/**
 * 名称: RabbitMQUtil.java
 * @author gaoshudian
 */
public class RabbitMQUtil {

    //获取rabbitMQ通讯的通道
    public static Channel getChannel()throws Exception{
        //创建连接工厂
        ConnectionFactory factory = new ConnectionFactory();
        //设置RabbitMQ相关信息
        factory.setHost("47.100.34.11");
        factory.setUsername("admin");
        factory.setPassword("admin");
        factory.setPort(5672);
        factory.setVirtualHost("/test");
        //创建一个新的连接
        Connection connection = factory.newConnection();
        //连接关闭时执行
        connection.addShutdownListener(new ShutdownListener() {
            public void shutdownCompleted(ShutdownSignalException e) {
                System.out.println("拦截关闭");
            }
        });
        //创建一个通道
        Channel channel = connection.createChannel();
        return channel;
    }
}
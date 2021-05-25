package com.gao.rabbitmq.director;

/**
 *   名称: HelloReceiver1.java
 *   描述:
 *   类型: JAVA
 *   最近修改时间:2018/5/30 15:37
 *   @version [版本号, V1.0]
 *   @since 2018/5/30 15:37
 *   @author gaoshudian
 */
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
public class DirectReceiver {

    @RabbitListener(queues = {RabbitConfig.TRADE_QUUE})
    public void process(@Payload DirectEntity directEntity) {
        System.out.println("接收消息:" + directEntity);
    }

}
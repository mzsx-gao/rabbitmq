package com.gao.rabbitmq.director;

/**
 *   名称: RabbitTest.java
 *   描述:
 *   类型: JAVA
 *   最近修改时间:2018/5/30 15:37
 *   @version [版本号, V1.0]
 *   @since 2018/5/30 15:37
 *   @author gaoshudian
 */
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/rabbit")
public class DirectSender {

    @Autowired
    private AmqpTemplate rabbitTemplate;

    @PostMapping("/hello/direct")
    public void hello() {
        DirectEntity directEntity = new DirectEntity();
        directEntity.setId("1");
        directEntity.setMessage("消息:direct");
        rabbitTemplate.convertAndSend(RabbitConfig.DEFAULT_DIRECT_EXCHANGE,RabbitConfig.TRADE_ROUTE_KEY,directEntity);
//        rabbitTemplate.convertAndSend(RabbitConfig.TRADE_ROUTE_KEY,directEntity);
        System.out.println("发送成功");
    }
}
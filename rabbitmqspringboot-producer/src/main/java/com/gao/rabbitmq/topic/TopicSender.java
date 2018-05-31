package com.gao.rabbitmq.topic;

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
public class TopicSender {

    @Autowired
    private AmqpTemplate rabbitTemplate;

    @PostMapping("/hello/topic")
    public void hello() {
        TopicEntity directEntity = new TopicEntity();
        directEntity.setId("1");
        directEntity.setMessage("消息:topic");
        //生产端发送请求, 其实就是指定Exchange与Route Key, 对于Queue来说生产者不关心
        rabbitTemplate.convertAndSend(RabbitConfig.DEFAULT_TOPIC_EXCHANGE,RabbitConfig.TOPIC_ROUTE_KEY,directEntity);
        System.out.println("发送成功");
    }
}
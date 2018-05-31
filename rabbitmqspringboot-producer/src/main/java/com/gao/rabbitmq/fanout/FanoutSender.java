package com.gao.rabbitmq.fanout;

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
public class FanoutSender {

    @Autowired
    private AmqpTemplate rabbitTemplate;

    @PostMapping("/hello/fanout")
    public void hello() {
        FanoutEntity directEntity = new FanoutEntity();
        directEntity.setId("1");
        directEntity.setMessage("消息:fanout");
        rabbitTemplate.convertAndSend(RabbitConfig.DEFAULT_FANOUT_EXCHANGE,"",directEntity);
        System.out.println("发送成功");
    }
}
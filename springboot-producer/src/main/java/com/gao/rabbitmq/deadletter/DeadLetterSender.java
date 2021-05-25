package com.gao.rabbitmq.deadletter;

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
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;


@RestController
@RequestMapping("/rabbit")
public class DeadLetterSender {

    @Autowired
    private AmqpTemplate rabbitTemplate;

    @PostMapping("/hello/dead")
    public void deadLetter(String p) {
        //声明消息处理器  这个对消息进行处理
        // 可以设置一些参数对消息进行一些定制化处理,我们这里来设置消息的编码以及消息的过期时间
        MessagePostProcessor messagePostProcessor = message -> {
            MessageProperties messageProperties = message.getMessageProperties();
            //设置编码
            messageProperties.setContentEncoding("utf-8");
            //设置过期时间10*1000毫秒
            messageProperties.setExpiration("5000");
            return message;
        };
        //向DL_QUEUE 发送消息  10*1000毫秒后过期 形成死信
        rabbitTemplate.convertAndSend("channel_exchange", "channel_queue", p, messagePostProcessor);
        System.out.println("发送成功...");
    }
}
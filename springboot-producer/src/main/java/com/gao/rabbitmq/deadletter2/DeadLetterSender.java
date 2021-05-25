package com.gao.rabbitmq.deadletter2;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.CountDownLatch;

@RestController
@RequestMapping("/rabbit")
public class DeadLetterSender {

    @Autowired
    private AmqpTemplate rabbitTemplate;

    @PostMapping("/hello/testDelayQueuePerMessageTTL")
    public void testDelayQueuePerMessageTTL() throws InterruptedException {
        for (int i = 1; i <= 3; i++) {
            long expiration = i * 1000;
            rabbitTemplate.convertAndSend(QueueConfig.DELAY_QUEUE_PER_MESSAGE_TTL_NAME,
                    (Object) ("delay_queue_per_message_ttl消息,过期时间:" + expiration),
                    new ExpirationMessagePostProcessor(expiration));
        }
    }

    @PostMapping("/hello/testDelayQueuePerQueueTTL")
    public void testDelayQueuePerQueueTTL() throws InterruptedException {
        for (int i = 1; i <= 3; i++) {
            rabbitTemplate.convertAndSend(QueueConfig.DELAY_QUEUE_PER_QUEUE_TTL_NAME,
                    "delay_queue_per_queue_ttl消息,过期时间:" + QueueConfig.QUEUE_EXPIRATION);
        }
    }

    @PostMapping("/hello/testFailMessage")
    public void testFailMessage() throws InterruptedException {
        for (int i = 1; i <= 3; i++) {
            rabbitTemplate.convertAndSend(QueueConfig.DELAY_PROCESS_QUEUE_NAME, ProcessReceiver.FAIL_MESSAGE);
        }
    }
}
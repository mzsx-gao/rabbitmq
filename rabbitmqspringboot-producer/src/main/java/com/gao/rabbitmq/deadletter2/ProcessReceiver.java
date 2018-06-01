package com.gao.rabbitmq.deadletter2;

import com.rabbitmq.client.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.ChannelAwareMessageListener;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Objects;
import java.util.concurrent.CountDownLatch;

@Component
public class ProcessReceiver implements ChannelAwareMessageListener {

    public static CountDownLatch latch;
    private static Logger logger = LoggerFactory.getLogger(ProcessReceiver.class);

    public static final String FAIL_MESSAGE = "This message will fail";

    @Override
    public void onMessage(Message message, Channel channel) throws Exception {
        try {
            processMessage(message);
        }catch (Exception e) {
            // 如果发生了异常，则将该消息重定向到缓冲队列，会在一定延迟之后自动重做
            channel.basicPublish(QueueConfig.PER_QUEUE_TTL_EXCHANGE_NAME, QueueConfig.DELAY_QUEUE_PER_QUEUE_TTL_NAME, null,
                    "失败消息过会儿会自动重试".getBytes());
        }
    }

    /**
     * 模拟消息处理。如果当消息内容为FAIL_MESSAGE的话，则需要抛出异常
     *
     * @param message
     * @throws Exception
     */
    public void processMessage(Message message) throws Exception {
        String realMessage = new String(message.getBody());
        logger.info("消费:" + realMessage);
        if (Objects.equals(realMessage, FAIL_MESSAGE)) {
            throw new Exception("系统异常");
        }
    }

}
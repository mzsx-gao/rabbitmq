package com.gao.rabbitmq;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;


@Service("mqProducer")
public class MQProducerImpl implements MQProducer{

    @Autowired
    private AmqpTemplate amqpTemplate;

    @Override
    public void sendDataToQueue(String exchangeKey,String queueKey, Object object) {

        try {
            amqpTemplate.convertAndSend(exchangeKey,queueKey, object);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
package com.gao.rabbitmq;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
public class QueueListener implements MessageListener {

    @Override
    public void onMessage(Message msg) {
        try{
            System.out.println("消费者..."+new String(msg.getBody()));
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
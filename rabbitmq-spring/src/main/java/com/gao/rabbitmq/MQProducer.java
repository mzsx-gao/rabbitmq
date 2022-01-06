package com.gao.rabbitmq;

public interface MQProducer {
    /**
     * 发送消息到指定队列
     * @param queueKey
     * @param object
     */
    public void sendDataToQueue(String exchangeKey,String queueKey, Object object);
}

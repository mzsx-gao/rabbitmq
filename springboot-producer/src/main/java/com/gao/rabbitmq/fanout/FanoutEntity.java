package com.gao.rabbitmq.fanout;

/**
 *   名称: TopicEntity.java
 *   描述:
 *   类型: JAVA
 *   最近修改时间:2018/5/31 11:29
 *   @version [版本号, V1.0]
 *   @since 2018/5/31 11:29
 *   @author gaoshudian
 */
public class FanoutEntity {

    private String id;
    private String message;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "TopicEntity{" +
                "id='" + id + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}
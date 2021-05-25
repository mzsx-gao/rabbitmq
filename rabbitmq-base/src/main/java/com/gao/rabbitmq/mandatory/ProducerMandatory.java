package com.gao.rabbitmq.mandatory;

import com.gao.rabbitmq.RabbitMQUtil;
import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * 生产者——失败确认模式
 * 在发送消息时设置 mandatory 标志，告诉 RabbitMQ，如果消息不可路由，应该将消息返回给发送者，并通知失败。可以这样认为，
 * 开启 mandatory是开启故障检测模式。
 * 注意：它只会让 RabbitMQ 向你通知失败，而不会通知成功。如果消息正确路由到队列，则发布者不会受到任何通知。
 * 带来的问题是无法确保发布消息一定是成功的，因为通知失败的消息可能会丢失
 */
public class ProducerMandatory {

    public final static String EXCHANGE_NAME = "mandatory_test";

    public static void main(String[] args) throws Exception {
        Channel channel = RabbitMQUtil.getChannel();
        channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.DIRECT);
        //信道关闭时执行
        channel.addShutdownListener(new ShutdownListener() {
            public void shutdownCompleted(ShutdownSignalException e) {
                System.out.println("信道关闭");
            }
        });
        //失败通知 回调
        channel.addReturnListener(new ReturnListener() {
            public void handleReturn(int replycode, String replyText, String exchange, String routeKey,
                                     AMQP.BasicProperties basicProperties, byte[] bytes) throws IOException {
                String message = new String(bytes);
                System.out.println("返回的replycode:"+replycode);
                System.out.println("返回的replyText:"+replyText);
                System.out.println("返回的exchange:"+exchange);
                System.out.println("返回的routeKey:"+routeKey);
            }
        });
        String[] routekeys={"king","mark","james"};
        for(int i=0;i<3;i++){
            String routekey = routekeys[i%3];
            // 发送的消息
            String message = "Hello World_"+(i+1) +("_"+System.currentTimeMillis());
            channel.basicPublish(EXCHANGE_NAME,routekey,true,null,message.getBytes());
            System.out.println("----------------------------------");
            System.out.println(" Sent Message: [" + routekey +"]:'" + message + "'");
            Thread.sleep(200);
        }
        // 关闭频道和连接
        channel.close();
        channel.getConnection().close();
    }
}
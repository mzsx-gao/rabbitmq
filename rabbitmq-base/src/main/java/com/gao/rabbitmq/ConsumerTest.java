package com.gao.rabbitmq;

import com.rabbitmq.client.*;
import org.junit.Test;

import java.io.IOException;

/**
 *  名称: ConsumerTest.java <br>
 *  描述: 客户端测试类<br>
 *  类型: JAVA <br>
 *  最近修改时间:2017/7/19 13:55.<br>
 *  @version [版本号, V1.0]
 *  @since 2017/7/19 13:55.
 *  @author gaoshudian
 */
public class ConsumerTest {

    //入门示例
    @Test
    public  void base() throws Exception{
        String QUEUE_NAME="rabbitMQ.publishConfirm";
        Channel channel = RabbitMQUtil.getChannel();
        //  声明一个队列
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        // 告诉服务器我们需要那个频道的消息，如果频道中有消息，就会执行回调函数handleDelivery
        Consumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope,
                                       AMQP.BasicProperties properties, byte[] body)
                    throws IOException {
                String message = new String(body, "UTF-8");
                System.out.println("消费者... '" + message + "'");
            }
        };
        //自动回复队列应答 -- RabbitMQ中的消息确认机制
        channel.basicConsume(QUEUE_NAME, true, consumer);
        Thread.sleep(Integer.MAX_VALUE);
    }

    /**
     * 任务分发
     *
     * 下面方法启动三次，分别为work1,work2,work3
     * 注：channel.basicQos(1);保证一次只分发一个
     *
     * rabbitmq应答机制
     * autoAck:是否自动回复
     * true:每次生产者只要发送信息就会从内存中删除，那么如果消费者程序异常退出，那么就无法获取数据.
     * false:手动回复  channel.basicAck(envelope.getDeliveryTag(),false)
     * 如果消费者异常退出，如果还有其他消费者，那么就会把队列中的消息发送给其他消费者，如果没有，等消费者启动时候再次发送
     * @throws Exception
     */
    @Test
    public void task_queue1() throws Exception{
        String QUEUE_NAME_TASK="task_queue";
        Channel channel = RabbitMQUtil.getChannel();
        //  声明一个队列
        channel.queueDeclare(QUEUE_NAME_TASK, true, false, false, null);

        //每次从队列获取的数量
        channel.basicQos(1);

        final Consumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag,
                                       Envelope envelope,
                                       AMQP.BasicProperties properties,
                                       byte[] body) throws IOException {
                String message = new String(body, "UTF-8");
                System.out.println("Worker1 消费消息... '" + message + "'");
                try {
                    Thread.sleep(1000);
                    throw  new Exception();
                }catch (Exception e){
                    System.out.println("worker1挂掉");
                    //关闭通道，模拟消费者宕机
                    channel.abort();
                }
                channel.basicAck(envelope.getDeliveryTag(),false);
            }
        };
        System.out.println("worker1等待...");
        //消息消费完成确认
        channel.basicConsume(QUEUE_NAME_TASK, false, consumer);
        Thread.sleep(Integer.MAX_VALUE);
    }
    @Test
    public void task_queue2() throws Exception{
        String QUEUE_NAME_TASK="task_queue";
        Channel channel = RabbitMQUtil.getChannel();
        //  声明一个队列
        channel.queueDeclare(QUEUE_NAME_TASK, true, false, false, null);

        //每次从队列获取的数量
        channel.basicQos(1);

        final Consumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag,
                                       Envelope envelope,
                                       AMQP.BasicProperties properties,
                                       byte[] body) throws IOException {
                String message = new String(body, "UTF-8");
                System.out.println("Worker2 消费消息... '" + message + "'");
                channel.basicAck(envelope.getDeliveryTag(),false);
            }
        };
        System.out.println("worker2等待...");
        //消息消费完成确认
        channel.basicConsume(QUEUE_NAME_TASK, false, consumer);
        Thread.sleep(Integer.MAX_VALUE);
    }

    //发布订阅模式
    @Test
    public void fanoutTest1() throws Exception{

        Channel channel = RabbitMQUtil.getChannel();

        String EXCHANGE_NAME="logs";
        channel.exchangeDeclare(EXCHANGE_NAME, "fanout");
        //产生一个随机的队列名称
        String queueName = channel.queueDeclare().getQueue();
        //String queue, String exchange, String routingKey
        channel.queueBind(queueName, EXCHANGE_NAME, "");//对队列进行绑定

        System.out.println("消费者1等待...");
        Consumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                String message = new String(body, "UTF-8");
                System.out.println("消费者1消费消息... '" + message + "'");
            }
        };
        channel.basicConsume(queueName, true, consumer);//队列会自动删除
        Thread.sleep(Integer.MAX_VALUE);
    }
    @Test
    public void fanoutTest2() throws Exception{

        Channel channel = RabbitMQUtil.getChannel();

        String EXCHANGE_NAME="logs";
        channel.exchangeDeclare(EXCHANGE_NAME, "fanout");
        //产生一个随机的队列名称
        String queueName = channel.queueDeclare().getQueue();
        //String queue, String exchange, String routingKey
        channel.queueBind(queueName, EXCHANGE_NAME, "");//对队列进行绑定

        System.out.println("消费者2等待...");
        Consumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                String message = new String(body, "UTF-8");
                System.out.println("消费者2消费消息... '" + message + "'");
            }
        };
        channel.basicConsume(queueName, true, consumer);//队列会自动删除
        Thread.sleep(Integer.MAX_VALUE);
    }

    //直连模式
    @Test
    public void routingTest1() throws Exception{

        // 交换器名称
        String EXCHANGE_NAME = "direct_logs";
        // 路由关键字
        String[] routingKeys = new String[]{"info","warning"};

        Channel channel = RabbitMQUtil.getChannel();

        //声明交换器
        channel.exchangeDeclare(EXCHANGE_NAME, "direct");
        //获取匿名队列名称
        String queueName=channel.queueDeclare().getQueue();

        //根据路由关键字进行绑定
        for (String routingKey:routingKeys){
            channel.queueBind(queueName,EXCHANGE_NAME,routingKey);
            System.out.println("消费者1.. 交换机:"+EXCHANGE_NAME+"," + " 队列:"+queueName+", routingKey:" + routingKey);
        }
        System.out.println("消费者1等待...");
        Consumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                String message = new String(body, "UTF-8");
                System.out.println("消费者1... '" + envelope.getRoutingKey() + "':'" + message + "'");
            }
        };
        channel.basicConsume(queueName, true, consumer);
        Thread.sleep(Integer.MAX_VALUE);
    }

    @Test
    public void routingTest2() throws Exception{

        // 交换器名称
        String EXCHANGE_NAME = "direct_logs";
        // 路由关键字
        String[] routingKeys = new String[]{"error"};

        Channel channel = RabbitMQUtil.getChannel();

        //声明交换器
        channel.exchangeDeclare(EXCHANGE_NAME, "direct");
        //获取匿名队列名称
        String queueName=channel.queueDeclare().getQueue();

        //根据路由关键字进行绑定
        for (String routingKey:routingKeys){
            channel.queueBind(queueName,EXCHANGE_NAME,routingKey);
            System.out.println("消费者2.. 交换机:"+EXCHANGE_NAME+"," +
                    " 队列:"+queueName+", routingKey:" + routingKey);
        }
        System.out.println("消费者2等待...");
        Consumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                String message = new String(body, "UTF-8");
                System.out.println("消费者2... '" + envelope.getRoutingKey() + "':'" + message + "'");
            }
        };
        channel.basicConsume(queueName, true, consumer);
        Thread.sleep(Integer.MAX_VALUE);
    }

    /**
     * topic模式
     */
    @Test
    public void topicTest1() throws Exception{

        Channel channel = RabbitMQUtil.getChannel();

        // 交换器名称
        String EXCHANGE_NAME = "topic_logs";
        //路由关键字
        String[] routingKeys = new String[]{"*.orange.*"};
        //声明一个匹配模式的交换机
        channel.exchangeDeclare(EXCHANGE_NAME, "topic");
        String queueName = channel.queueDeclare().getQueue();
        //绑定路由
        for (String routingKey : routingKeys) {
            channel.queueBind(queueName, EXCHANGE_NAME, routingKey);
            System.out.println("topic1 交换机:" + EXCHANGE_NAME + ", 队列:" + queueName + ", 绑定routingKey:" + routingKey);
        }
        System.out.println("topic1等待...");

        Consumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                String message = new String(body, "UTF-8");
                System.out.println("topic1消费... key:'" + envelope.getRoutingKey() + "':'" + message + "'");
            }
        };
        channel.basicConsume(queueName, true, consumer);
        Thread.sleep(Integer.MAX_VALUE);
    }
    @Test
    public void topicTest2() throws Exception{

        Channel channel = RabbitMQUtil.getChannel();

        // 交换器名称
        String EXCHANGE_NAME = "topic_logs";
        //路由关键字
        String[] routingKeys = new String[]{"*.*.rabbit", "lazy.#"};
        //声明一个匹配模式的交换机
        channel.exchangeDeclare(EXCHANGE_NAME, "topic");
        String queueName = channel.queueDeclare().getQueue();
        //绑定路由
        for (String routingKey : routingKeys) {
            channel.queueBind(queueName, EXCHANGE_NAME, routingKey);
            System.out.println("topic2 交换机:" + EXCHANGE_NAME + ", 队列:" + queueName + ", 绑定routingKey:" + routingKey);
        }
        System.out.println("topic2等待...");

        Consumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                String message = new String(body, "UTF-8");
                System.out.println("topic2消费... key:" + envelope.getRoutingKey() + "消息:" + message + "'");
            }
        };
        channel.basicConsume(queueName, true, consumer);
        Thread.sleep(Integer.MAX_VALUE);
    }

}
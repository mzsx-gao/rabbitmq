package com.gao.rabbitmq;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.MessageProperties;
import org.junit.Test;

/**
 *  名称: ProducerTest.java <br>
 *  描述: 服务方测试类<br>
 *  类型: JAVA <br>
 *  最近修改时间:2017/7/19 12:27.<br>
 *  @version [版本号, V1.0]
 *  @since 2017/7/19 12:27.
 *  @author gaoshudian
 */
public class ProducerTest {

    public final static String QUEUE_NAME="rabbitMQ.test";
    public final static String QUEUE_NAME_TASK="task_queue";

    //入门示例
    @Test
    public void base() throws Exception{
        Channel channel = RabbitMQUtil.getChannel();
        //  声明一个队列
        /**
         * String queue：队列名称
         * boolean durable:是否持久化（true表示是，队列将在服务器重启时生存）
         * boolean exclusive:是否是独占队列（创建者可以使用的私有队列，断开后自动删除）
         * boolean autoDelete:当所有消费者客户端连接断开时是否自动删除队列
         * Map<String, Object> arguments:队列的其他参数
         */
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        String message = "Hello RabbitMQ";
        //发送消息到队列中
        /**
         * String exchange
         * String routingKey
         * BasicProperties props
         * byte[] body
         */
        channel.basicPublish("", QUEUE_NAME, null, message.getBytes("UTF-8"));
        System.out.println("ProducerTest Send +'" + message + "'");
        //关闭通道和连接
        channel.close();
        channel.getConnection().close();
    }

    //任务分发
    @Test
    public void task_queue() throws Exception{

        Channel channel = RabbitMQUtil.getChannel();
        //  声明一个队列
        channel.queueDeclare(QUEUE_NAME_TASK, true, false, false, null);
        //分发信息
        for (int i=0;i<10;i++){
            String message="Hello RabbitMQ"+(i+1);
            //因为在第一个参数选择了默认的exchange，而我们申明的队列叫task_queue，所以默认的，它在新建一个也叫task_queue的routingKey，并绑定在默认的exchange上，
            //导致了我们可以在第二个参数routingKey中写TaskQueue，这样它就会找到定义的同名的queue，并把消息放进去。
            channel.basicPublish("",QUEUE_NAME_TASK, MessageProperties.PERSISTENT_TEXT_PLAIN,message.getBytes());
            System.out.println("发送消息...'"+message+"'");
        }

        //关闭通道和连接
        channel.close();
        channel.getConnection().close();
    }

    //发布订阅模式
    @Test
    public void fanoutTest() throws Exception{

        Channel channel = RabbitMQUtil.getChannel();

        String EXCHANGE_NAME="logs";
        //声明交换机
        channel.exchangeDeclare(EXCHANGE_NAME,"fanout");//fanout表示分发，所有的消费者得到同样的队列信息
        //分发信息
        for (int i=0;i<10;i++){
            String message="Hello World..."+i;
            channel.basicPublish(EXCHANGE_NAME,"",null,message.getBytes());
            System.out.println("EmitLog Sent '" + message + "'");
        }

        //关闭通道和连接
        channel.close();
        channel.getConnection().close();
    }

    //直连模式
    @Test
    public void routingTest() throws Exception{

        String EXCHANGE_NAME="direct_logs";
        // 路由关键字
        String[] routingKeys = new String[]{"info" ,"warning", "error"};
        Channel channel = RabbitMQUtil.getChannel();

        //声明交换机
        channel.exchangeDeclare(EXCHANGE_NAME,"direct");//注意是direct
        //发送信息
        for (String routingKey:routingKeys){
            String message = "routing key:" + routingKey;
            channel.basicPublish(EXCHANGE_NAME,routingKey,null,message.getBytes());
            System.out.println("生产消息..."+routingKey +"':'" + message);
        }

        //关闭通道和连接
        channel.close();
        channel.getConnection().close();
    }

    /**
     * topic模式
     * 这种应该属于模糊匹配
     *  *：可以替代一个词
     *  #：可以替代0或者更多的词
     */
    @Test
    public void topicTest() throws Exception{

        String EXCHANGE_NAME="topic_logs";
        // 路由关键字
        Channel channel = RabbitMQUtil.getChannel();

        //声明一个匹配模式的交换机
        channel.exchangeDeclare(EXCHANGE_NAME,"topic");
        //待发送的消息
        String[] routingKeys=new String[]{
                "quick.orange.rabbit",
                "lazy.orange.elephant",
                "quick.orange.fox",
                "lazy.brown.fox",
                "quick.brown.fox",
                "quick.orange.male.rabbit",
                "lazy.orange.male.rabbit"
        };
        //发送消息
        for(String severity :routingKeys){
            channel.basicPublish(EXCHANGE_NAME, severity, null, severity.getBytes());
            System.out.println("生产...交换机:"+EXCHANGE_NAME+" ,key:" + severity + ",消息:'" + severity + "'");
        }

        //关闭通道和连接
        channel.close();
        channel.getConnection().close();
    }
}
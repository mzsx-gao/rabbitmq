import com.gao.rabbitmq.MQProducer;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import java.util.HashMap;
import java.util.Map;


@RunWith(value = SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext.xml"})
public class TestQueue {

    @Autowired
    private MQProducer mqProducer;

    @Test
    @Ignore
    public void send() throws Exception {

        String[] routingKeys = new String[]{"info" ,"warning", "error"};
        for (String routingKey:routingKeys){
            Map<String,Object> msg = new HashMap<>();
            msg.put("data","hello.."+routingKey+"!");
            String message = "routing key:" + routingKey;
            mqProducer.sendDataToQueue("publishConfirm-mq-exchange",routingKey,msg);
            System.out.println("生产消息..."+routingKey +"':'" + message);
        }
        Thread.sleep(Integer.MAX_VALUE);

    }
}
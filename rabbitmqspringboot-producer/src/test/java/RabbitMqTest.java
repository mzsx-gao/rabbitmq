import com.gao.rabbitmq.Producer;
import com.gao.rabbitmq.StartupProducer;
import junit.framework.TestCase;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = StartupProducer.class)
public class RabbitMqTest extends TestCase {

    @Autowired
    private Producer producer;

    @Test
    public void test() throws InterruptedException {
        producer.sendMsg("hello world");
        Thread.sleep(Integer.MAX_VALUE);
    }
}

import com.gao.rabbitmq.Producer;
import com.gao.rabbitmq.Reqe;
import com.gao.rabbitmq.Reqe2;
import com.gao.rabbitmq.StartupProducer;
import junit.framework.TestCase;
import org.json.JSONObject;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = StartupProducer.class)
public class RabbitMqTest extends TestCase {

    @Autowired
    private Producer producer;

    @Test
    public void test() throws InterruptedException {
        Reqe reqe = new Reqe();
        reqe.setBatch_id("batch_id");
        reqe.setAppCode("app_code");

        List<Reqe2> reqe2List = new ArrayList<>();
        Reqe2 reqe2 = new Reqe2();
        reqe2.setAccount_category("account_ca");
        reqe2List.add(reqe2);

        reqe.setList(reqe2List);

        producer.sendMsg("{\"batch_id\":\"batch_id\",\"interface_id\":null,\"tenantCode\":null,\"appCode\":\"app_code\",\"token\":null,\"buCode\":null,\"list\":[{\"serial_no\":null,\"name\":null,\"license_no\":null,\"account_no\":null,\"account_category\":\"account_ca\",\"back_no\":null,\"bank_branch_name\":null,\"bank_province_no\":null,\"bank_city_no\":null,\"bank_zone_no\":null,\"bank_branch_addr\":null}]}\n");
        Thread.sleep(Integer.MAX_VALUE);
    }
}

package com.gao.rabbitmq.director;

/**
 *   名称: RabbitTest.java
 *   描述:
 *   类型: JAVA
 *   最近修改时间:2018/5/30 15:37
 *   @version [版本号, V1.0]
 *   @since 2018/5/30 15:37
 *   @author gaoshudian
 */
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/rabbit")
public class DirectSender {

    @Autowired
    private AmqpTemplate rabbitTemplate;

    @PostMapping("/hello/direct")
    public void hello() {
        DirectEntity directEntity = new DirectEntity();
        directEntity.setId("1");
        directEntity.setMessage("消息:direct");
        rabbitTemplate.convertAndSend(RabbitConfig.DEFAULT_DIRECT_EXCHANGE,RabbitConfig.TRADE_ROUTE_KEY,directEntity);
//        rabbitTemplate.convertAndSend(RabbitConfig.TRADE_ROUTE_KEY,directEntity);
        System.out.println("发送成功");
    }

    //lenderb项目测试trade-order向lenderb-notify项目发送异步通知
    @PostMapping("/hello/notifyRecordTest")
    public void notifyRecordTest() {
        NotifyRecord notifyRecord = new NotifyRecord();
        notifyRecord.setMerchantCode("10");
        notifyRecord.setBuCode("C11");
        notifyRecord.setApplicationCode("1001");
        notifyRecord.setInterfaceId("LENDERB-CUST-LIMIT-CREATE-NOTIFY-0054");
        notifyRecord.setOrderNo("10C1110010053201710230157596660250");
        notifyRecord.setReqData("{\"tradeNo\":\"10C1110010053201710230157596660250\",\"outTradeNo\":\"10121010010053201904221635167580004\",\"tradeStatus\":\"30\",\"finishDateTime\":\"2019-04-23 14:12:01\",\"creditNo\":\"00001\",\"thirdCreditNo\":\"00001\",\"thirdCreditAmt\":\"10000\",\"thirdCreditSignEndDate\":\"20200418\"}");
        rabbitTemplate.convertAndSend("dev.lenderb_exchange","dev.lenderb_single_notify",notifyRecord);
        System.out.println("发送成功");
    }

    //lenderb项目测试各服务向lenderb-tss项目发送异步通知
    @PostMapping("/hello/notifyTssTest")
    public void notifyTssTest() {
        ServiceRetryEntity entity = new ServiceRetryEntity();
        entity.setInterfaceId("LENDERB-CUST-LIMIT-CREATE-NOTIFY-0054");
        entity.setMerchantCode("10");
        entity.setBuCode("C11");
        entity.setApplicationCode("1001");
        entity.setDelay(10);
        entity.setTradeNo("10C1110010053201710230157596660250");
        entity.setTargetServiceName("lenderb_channel");
        entity.setData("{\"interfaceId\":\"LENDERB-CUST-LIMIT-CREATE-NOTIFY-0054\",\"merchantCode\":\"10\",\"buCode\":\"C11\",\"applicationCode\":\"1001\",\"tradeNo\":\"10C1110010053201710230157596660250\",\"interfaceSync\":\"0\",\"data\":{\"tradeNo\":\"10C1110010053201710230157596660250\",\"tradeStatus\":\"30\",\"creditNo\":\"00001\",\"thirdCreditNo\":\"00001\",\"thirdCreditAmt\":\"10000\",\"thirdCreditSignDate\":\"20190418\",\"thirdCreditSignEndDate\":\"20200418\",\"outTradeNo\":\"10121010010053201904221635167580004\"},\"extra\":{}}");
        rabbitTemplate.convertAndSend("dev.lenderb_exchange","dev.lenderb_tss_service_retry",entity);
        System.out.println("发送成功");
    }


}
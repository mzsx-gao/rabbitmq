package com.gao.springretry.demo1;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.remoting.RemoteAccessException;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import java.time.LocalTime;


/*
1、使用了@Retryable的方法不能在本类被调用，不然重试机制不会生效。也就是要标记为@Service，然后在其它类使用@Autowired注入或者@Bean去实例才能生效。
2、要触发@Recover方法，那么在@Retryable方法上不能有返回值，只能是void才能生效。
3、使用了@Retryable的方法里面不能使用try...catch包裹，要在发放上抛出异常，不然不会触发。
4、在重试期间这个方法是同步的，如果使用类似Spring Cloud这种框架的熔断机制时，可以结合重试机制来重试后返回结果。
5、Spring Retry不只能注入方式去实现，还可以通过API的方式实现，类似熔断处理的机制就基于API方式实现会比较宽松。
 */
@Service
public class PayService {

    private Logger logger = LoggerFactory.getLogger(getClass());

    private final int totalNum = 100000;

    /*
        value值表示当哪些异常的时候触发重试
        maxAttempts表示最大重试次数默认为3
        delay表示重试的延迟时间
        multiplier表示延迟倍数:
            默认为0，表示固定暂停1秒后进行重试，如果把multiplier设置为2，则第一次重试为1秒，第二次为2秒，第三次为4秒
     */
    @Retryable(value = RemoteAccessException.class,maxAttempts = 3,backoff = @Backoff(delay = 2000,multiplier = 1.5))
    public void minGoodsnum(int num) throws Exception{
        logger.info("minGoodsnum开始"+ LocalTime.now());
        if(num <= 0){
            //模拟调用三方网络异常
            throw new RemoteAccessException("远程调用异常");
        }
        logger.info("minGoodsnum执行结束");
    }

    //@Recover：用于@Retryable重试失败后处理方法，此方法里的异常一定要是@Retryable方法里抛出的异常，否则不会调用
    @Recover
    public void recover(RemoteAccessException e){
        logger.info("达到最大重试次数...");
        logger.info(e.getMessage());
    }
}
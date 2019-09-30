package com.lcg.sample;

import com.lcg.sample.config.DubboServiceConfig;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.concurrent.CountDownLatch;

/**
 * @author linchuangang
 * @create 2019-09-27 8:35 PM
 **/
public class Service2Application {

    public static void main(String...args)throws Exception{
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(DubboServiceConfig.class);
        context.start();
        new CountDownLatch(1).await();
    }
}

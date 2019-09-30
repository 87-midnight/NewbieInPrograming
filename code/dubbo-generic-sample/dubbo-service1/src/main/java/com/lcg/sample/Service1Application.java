package com.lcg.sample;

import com.lcg.sample.config.ServiceNodeConfig;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.concurrent.CountDownLatch;

/**
 * @author linchuangang
 * @create 2019-09-27 6:22 PM
 **/
public class Service1Application {

    public static void main(String...args)throws Exception{
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(ServiceNodeConfig.class);
        context.start();
        new CountDownLatch(1).await();
    }
}

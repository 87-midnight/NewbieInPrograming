package com.lcg.sample;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.concurrent.CountDownLatch;

public class XmlProviderBootstrap {

    public static void main(String...args)throws Exception{
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("dubbo-provider.xml");
        context.start();
        new CountDownLatch(1).await();
    }
}

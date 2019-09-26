package com.lcg.sample;

import com.lcg.sample.config.ConsumerConfiguration;
import com.lcg.sample.service.PrintService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.concurrent.CountDownLatch;

public class AnnotationConsumerBootstrap {
    private static Logger log = LoggerFactory.getLogger(AnnotationConsumerBootstrap.class);

    public static void main(String...args)throws Exception{
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(ConsumerConfiguration.class);
        context.start();
        log.info("annotation consumer application started");
        PrintService printService = context.getBean(PrintService.class);
        log.info("invoke by component,result:{}",printService.readyPrint("on ready"));
        new CountDownLatch(1).await();
    }
}

package com.lcg.sample;

import com.lcg.sample.config.ProviderConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.concurrent.CountDownLatch;

public class AnnotationProviderBootstrap {

    private static Logger log = LoggerFactory.getLogger(AnnotationProviderBootstrap.class);

    public static void main(String...args)throws Exception{
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(ProviderConfiguration.class);
        context.start();
        log.info("annotation provider application started");
        new CountDownLatch(1).await();
    }
}

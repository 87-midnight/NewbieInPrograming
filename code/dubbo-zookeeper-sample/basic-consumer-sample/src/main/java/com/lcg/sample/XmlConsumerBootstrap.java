package com.lcg.sample;

import com.lcg.sample.service.EchoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class XmlConsumerBootstrap {

    private static Logger log = LoggerFactory.getLogger(XmlConsumerBootstrap.class);

    public static void main(String...args)throws Exception{
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("dubbo-consumer.xml");
        context.start();
        EchoService echoService = context.getBean(EchoService.class);
        String result = echoService.print("hello,provider,I'm customer");
        log.warn("echoService invoke result:{}",result);
    }
}

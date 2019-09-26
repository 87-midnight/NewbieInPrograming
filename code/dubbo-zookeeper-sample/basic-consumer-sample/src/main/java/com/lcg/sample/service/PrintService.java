package com.lcg.sample.service;

import org.apache.dubbo.config.annotation.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class PrintService {

    @Reference
    private EchoService echoService;

    private Logger log = LoggerFactory.getLogger(this.getClass());

    public String readyPrint(String msg){
        String result = echoService.print(msg);
        log.info("annotation invoke result:{}",result);
        return result;
    }
}

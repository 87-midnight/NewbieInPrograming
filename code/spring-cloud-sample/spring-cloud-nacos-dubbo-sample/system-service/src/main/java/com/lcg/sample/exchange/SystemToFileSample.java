package com.lcg.sample.exchange;

import com.lcg.sample.BasePrintService;
import com.lcg.sample.BaseUserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

/**
 * @author linchuangang
 * @createTime 2020/10/25
 **/
@Component
@Slf4j
public class SystemToFileSample implements ApplicationListener<ContextRefreshedEvent> {

    @Autowired
    @Lazy
    private RestTemplate restTemplate;


    @Autowired
    @Qualifier("dubboRest")
    private RestTemplate dubboRestTemplate;

    @Reference
    private BasePrintService basePrintService;

    @Autowired
    private SystemDubboFeignChange systemDubboFeignChange;

    @Autowired
    private SystemFeignChange systemFeignChange;


    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        try {
            String result = this.restTemplate.getForEntity("lb://system-application/save?id="+System.currentTimeMillis(),String.class).getBody();
            log.info("rest template http调用file服务结果：{}",result);
            String result1 = this.dubboRestTemplate.getForEntity("lb://system-application/save?id="+System.currentTimeMillis(),String.class).getBody();
            log.info("dubbo rest template http调用file服务结果：{}",result1);
            String result2 = this.basePrintService.print("John");
            log.info("dubbo service 调用file服务结果：{}",result2);

            String result3 = this.systemDubboFeignChange.getFile("John=123");
            log.info("dubbo feign 调用file服务结果：{}",result3);

            String result4 = this.systemFeignChange.getFile("John=456");
            log.info("feign http 调用file服务结果：{}",result4);

        }finally {

        }
    }
}

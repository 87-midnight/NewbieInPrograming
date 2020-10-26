package com.lcg.sample.exchange;

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
public class FileToSystemSample implements ApplicationListener<ContextRefreshedEvent> {

    @Autowired
    @Lazy
    private RestTemplate restTemplate;


    @Autowired
    @Qualifier("dubboRest")
    private RestTemplate dubboRestTemplate;

    @Reference
    private BaseUserService baseUserService;


    @Autowired
    private FileDubboFeignChange fileDubboFeignChange;

    @Autowired
    private FileFeignChange fileFeignChange;



    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        try {
            String result = this.restTemplate.getForEntity("lb://system-application/save?id="+System.currentTimeMillis(),String.class).getBody();
            log.info("rest template http调用system服务结果：{}",result);
            String result1 = this.dubboRestTemplate.getForEntity("lb://system-application/save?id="+System.currentTimeMillis(),String.class).getBody();
            log.info("dubbo rest template http调用system服务结果：{}",result1);
            String result2 = this.baseUserService.get("Tom");
            log.info("dubbo service 调用system服务结果：{}",result2);

            String result3 = this.fileDubboFeignChange.saveUser("Tom=123");
            log.info("dubbo feign 调用system服务结果：{}",result3);

            String result4 = this.fileFeignChange.saveUser("Tom=456");
            log.info("feign http 调用system服务结果：{}",result4);

        }finally {

        }
    }
}

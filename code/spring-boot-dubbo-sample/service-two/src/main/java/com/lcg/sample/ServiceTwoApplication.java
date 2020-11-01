package com.lcg.sample;

import com.lcg.sample.common.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

/**
 * @author linchuangang
 * @createTime 2020/11/1
 **/
@SpringBootApplication
@Slf4j
@EnableScheduling
public class ServiceTwoApplication {
    public static void main(String[] args) {
        SpringApplication.run(ServiceTwoApplication.class,args);
    }

    @Reference
    private UserService userService;

    @Scheduled(cron = "0/3 * * * * ?")
    public void test(){
        log.info("服务2调用服务1的userService结果：{}",userService.getUserName());
    }
}

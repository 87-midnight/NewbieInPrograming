package com.lcg.sample;

import com.lcg.sample.common.DeviceService;
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
public class ServiceOneApplication {
    public static void main(String[] args) {
        SpringApplication.run(ServiceOneApplication.class,args);
    }
    @Reference
    private DeviceService deviceService;

    @Scheduled(cron = "0/3 * * * * ?")
    public void test(){
        log.info("服务1调用服务2的deviceService结果：{}",deviceService.getDeviceName());
    }
}

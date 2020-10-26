package com.lcg.sample;

import com.alibaba.cloud.dubbo.annotation.DubboTransported;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.web.client.RestTemplate;

/**
 * @author linchuangang
 * @createTime 2020/10/22
 **/
@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
public class SystemServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(SystemServiceApplication.class,args);
    }

    @Bean("dubboRest")
    @LoadBalanced
    @DubboTransported
    public RestTemplate dubboRestTemplate(){
        return new RestTemplate();
    }

    @Bean
    @Primary
    @LoadBalanced
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }
}

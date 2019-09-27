package com.lcg.sample.config;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * @author linchuangang
 * @create 2019-09-27 6:24 PM
 **/
@Configuration
@EnableDubbo(scanBasePackages = {"com.lcg.sample.service"})
@PropertySource("classpath:dubbo-service1.properties")
public class ServiceNodeConfig {
}

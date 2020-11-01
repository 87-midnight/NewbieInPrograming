package com.lcg.sample.config;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * @author linchuangang
 * @createTime 2020/11/1
 **/
@Configuration
@EnableDubbo(scanBasePackages = {"com.lcg.sample.service"})
@PropertySource(value = "classpath:dubbo.properties")
public class DubboConfigurer {
}

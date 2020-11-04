package com.lcg.sample;

import org.redisson.api.RBucket;
import org.redisson.api.RedissonClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.core.annotation.Order;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * @author linchuangang
 * @createTime 2020/11/3
 **/
@SpringBootApplication
public class RedisApplication {

    private static Logger log = LoggerFactory.getLogger(RedisApplication.class);

    public static void main(String[] args) {
        new SpringApplicationBuilder(RedisApplication.class).web(WebApplicationType.NONE).run(args);
    }

    @Autowired
    private RedisTemplate<Object,Object> redisTemplate;

    @Autowired
    @Qualifier("redissonTemplate")
    private RedisTemplate<Object,Object> redissonTemplate;

    @Autowired
    private RedissonClient redissonClient;

    @Bean
    @Order(1)
    public CommandLineRunner write(){
        return args->{
//            redisTemplate.boundValueOps("test").set("hahaha1");
            redissonClient.getBucket("test1").set("test1");
            log.info("主数据库写入数据");
        };
    }

    @Bean
    @Order(2)
    public CommandLineRunner read(){
        return args->{
            // redisson与spring redistemplate对缓存对象的编解码方式不同，故客户端连接的读写只能使用同一种
            String result = (String) redissonTemplate.boundValueOps("test1").get();
            log.info("读数据：{}",result);
//            log.info("从数据库读取数据,{}",redissonTemplate.boundValueOps("test").get());
        };
    }
}

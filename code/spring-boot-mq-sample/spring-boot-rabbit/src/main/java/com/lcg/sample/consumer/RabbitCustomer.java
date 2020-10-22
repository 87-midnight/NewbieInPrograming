package com.lcg.sample.consumer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.HashMap;
import java.util.Map;

/**
 * @author linchuangang
 * @createTime 2020/10/22
 **/
@Configuration
@Slf4j
public class RabbitCustomer {

//广播策略

    @Bean
    public Queue test1Queue(){
        return new Queue("test-1");
    }
    @Bean
    public Queue test2Queue(){
        return new Queue("test-2");
    }

    /**
     * <p>方法描述:广播策略</p>
     * @author linchuangang
     * @create 2020/10/22 20:49
     * @param []
     * @return org.springframework.amqp.core.FanoutExchange
     **/
    @Bean
    public FanoutExchange fanoutExchange(){
        return new FanoutExchange("fanout");
    }
    @Bean
    public Binding test1Binding(){
        return BindingBuilder.bind(test1Queue()).to(fanoutExchange());
    }

    @Bean
    public Binding test2Binding(){
        return BindingBuilder.bind(test2Queue()).to(fanoutExchange());
    }

    @RabbitListener(queues = "test-1")
    public void test1Print(String msg){
        log.info("广播策略，test-1队列接收：{}",msg);
    }

    @RabbitListener(queues = "test-2")
    public void test2Print(String msg){
        log.info("广播策略，test-2队列接收：{}",msg);
    }
//广播策略


//直接策略

    @Bean
    public Queue test3Queue(){
        return new Queue("test-3");
    }

    @Bean
    public Queue test4Queue(){
        return new Queue("test-4");
    }

    @Bean
    public DirectExchange directExchange(){
        return new DirectExchange("direct");
    }

    @Bean
    public Binding test3Binding(){
        return BindingBuilder.bind(test3Queue()).to(directExchange()).with("routingKey.test3");
    }
    @Bean
    public Binding test4Binding(){
        return BindingBuilder.bind(test4Queue()).to(directExchange()).with("routingKey.test4");
    }


    @RabbitListener(queues = "test-3")
    public void test3Print(String msg){
        log.info("直接策略，test-3接收：{}",msg);
    }

    @RabbitListener(queues = "test-4")
    public void test4Print(String msg){
        log.info("直接策略，test-4接收：{}",msg);
    }


//直接策略


//topic策略

    @Bean
    public Queue test5Queue(){
        return new Queue("test-5");
    }
    @Bean
    public Queue test6Queue(){
        return new Queue("test-6");
    }
    @Bean
    public TopicExchange topicExchange(){
        return new TopicExchange("topic");
    }

    @Bean
    public Binding test5Binding(){
        return BindingBuilder.bind(test5Queue()).to(topicExchange()).with("routingKey.*");
    }
    @Bean
    public Binding test6Binding(){
        return BindingBuilder.bind(test6Queue()).to(topicExchange()).with("#");
    }
    @RabbitListener(queues = "test-5")
    public void test5Print(String msg){
        log.info("topic策略，test-5接收：{}",msg);
    }
    @RabbitListener(queues = "test-6")
    public void test6Print(String msg){
        log.info("topic策略，test-6接收：{}",msg);
    }
//topic策略

//header策略

    @Bean
    public Queue test7Queue(){
        return new Queue("test-7");
    }
    @Bean
    public Queue test8Queue(){
        return new Queue("test-8");
    }
    @Bean
    public HeadersExchange headersExchange(){
        return new HeadersExchange("headers");
    }

    @Bean
    public Binding test7Binding(){
        Map<String,Object> headers = new HashMap<>();
        headers.put("test7","test7");
        return BindingBuilder.bind(test7Queue()).to(headersExchange()).whereAll(headers).match();
    }
    @Bean
    public Binding test8Binding(){
        Map<String,Object> headers = new HashMap<>();
        headers.put("test8","test8");
        return BindingBuilder.bind(test8Queue()).to(headersExchange()).whereAll(headers).match();
    }
    @RabbitListener(queues = "test-7")
    public void test7Print(String msg){
        log.info("headers策略，test-7接收：{}",msg);
    }
    @RabbitListener(queues = "test-8")
    public void test8Print(String msg){
        log.info("headers策略，test-8接收：{}",msg);
    }

//header策略
}

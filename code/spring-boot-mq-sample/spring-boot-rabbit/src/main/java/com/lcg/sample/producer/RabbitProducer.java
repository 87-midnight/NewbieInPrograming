package com.lcg.sample.producer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.core.RabbitMessagingTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.HashMap;
import java.util.Map;

/**
 * @author linchuangang
 * @createTime 2020/10/22
 **/
@Configuration
@EnableScheduling
@Slf4j
public class RabbitProducer {


    @Autowired
    private AmqpTemplate amqpTemplate;

    /**
     * <p>方法描述:fanout策略测试</p>
     * @author linchuangang
     * @create 2020/10/22 20:59
     * @param []
     * @return void
     **/
    @Scheduled(cron = "2/5 * * * * ?")
    public void fanoutSend(){
        //param1:交换机名称，param2:路由键,param3:消息体
        amqpTemplate.convertAndSend("fanout","","hello");
    }

    /**
     * <p>方法描述:direct策略测试</p>
     * @author linchuangang
     * @create 2020/10/22 20:59
     * @param []
     * @return void
     **/
    @Scheduled(cron = "2/5 * * * * ?")
    public void directSend(){

        amqpTemplate.convertAndSend("direct","routingKey.test3","hello,direct");
    }

    /**
     * <p>方法描述:topic策略测试</p>
     * @author linchuangang
     * @create 2020/10/22 20:59
     * @param []
     * @return void
     **/
    @Scheduled(cron = "2/5 * * * * ?")
    public void topicSend(){

        amqpTemplate.convertAndSend("topic","routingKey.test5","hello,test5");
        amqpTemplate.convertAndSend("topic","routingKey.test51","hello,test51");
        amqpTemplate.convertAndSend("topic","routingKey12345","hello,test6");
    }

    @Autowired
    private RabbitMessagingTemplate rabbitMessagingTemplate;
    /**
     * <p>方法描述:headers策略测试</p>
     * @author linchuangang
     * @create 2020/10/22 20:59
     * @param []
     * @return void
     **/
    @Scheduled(cron = "2/5 * * * * ?")
    public void headersSend(){
        Map<String,Object> headers7 = new HashMap<>();
        headers7.put("test7","test7");
        rabbitMessagingTemplate.convertAndSend("headers","","hello,test7",headers7);
        rabbitMessagingTemplate.convertAndSend("headers","","hello,test71",headers7);
        rabbitMessagingTemplate.convertAndSend("headers","","hello,test72",headers7);
    }
}

package com.lcg.sample.teacher;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.binder.ConsumerProperties;
import org.springframework.cloud.stream.binder.ProducerProperties;
import org.springframework.cloud.stream.config.BindingProperties;
import org.springframework.cloud.stream.config.BindingServiceProperties;
import org.springframework.context.annotation.*;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

/**
 * @author linchuangang
 * @createTime 2020/10/31
 **/
@Configuration
@Profile("teacher")
@ComponentScan(value = "com.lcg.sample.teacher")
@EnableBinding(value = {TeacherChatChannel.class})
@EnableScheduling
public class TeacherConfigurer {


    @Bean
    @Primary
    public BindingServiceProperties travel(){
        BindingServiceProperties properties = new BindingServiceProperties();
        Map<String, BindingProperties> bindingPropertiesMap = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);

        //消息主题：生产者配置
        BindingProperties classroomOutputChannel = new BindingProperties();
        classroomOutputChannel.setContentType("application/json");
        classroomOutputChannel.setDestination("classroom-topic");
        ProducerProperties crawlerProducer = new ProducerProperties();
        crawlerProducer.setPartitionCount(10);
        classroomOutputChannel.setProducer(crawlerProducer);
        bindingPropertiesMap.put("classroom-output",classroomOutputChannel);

        properties.setBindings(bindingPropertiesMap);
        return properties;
    }


}

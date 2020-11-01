package com.lcg.sample.student;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.HashMap;
import java.util.Map;

/**
 * @author linchuangang
 * @createTime 2020/10/31
 **/
@Profile("student")
@Configuration
@ComponentScan(basePackages = "com.lcg.sample.student")
@EnableBinding(value = {StudentChatChannel.class})
@EnableScheduling
@Slf4j
public class StudentConfigurer {

    @Autowired
    private StudentChatChannel studentChatChannel;

    @Scheduled(cron = "0/4 * * * * ?")
    public void studentAChitchat(){
        Map info = new HashMap();
        info.put("name","studentA");
        info.put("words","how are you");
        info.put("timestamp",System.currentTimeMillis());
        MessageBuilder<Map> builder = MessageBuilder.withPayload(info);
        Message message = builder.build();
        studentChatChannel.studentA().send(message);
    }

    @StreamListener(value = "chitchat-input")
    public void studentBChitchat(Message<?> message){
        log.info("学生B接收学生A说的话：{}",message.getPayload());
    }

    @StreamListener(value = "classroom-input")
    public void classroomLesson1(Message<?> message){
        log.info("学生1接收老师说的话：{}",message.getPayload());
    }

    @StreamListener(value = "classroom-input1")
    public void classroomLesson2(Message<?> message){
        log.info("学生2接收老师说的话：{}",message.getPayload());
    }
}

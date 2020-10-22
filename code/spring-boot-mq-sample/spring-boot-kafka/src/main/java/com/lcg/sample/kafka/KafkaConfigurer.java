package com.lcg.sample.kafka;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @author linchuangang
 * @createTime 2020/10/22
 **/
@Configuration
@EnableScheduling
@Slf4j
public class KafkaConfigurer {

    @Bean
    public NewTopic test1(){
        return new NewTopic("test-1",5,(short)5);
    }

    @KafkaListener(topics = {"test-1"},groupId = "test1-local")
    public void test1Print(Message<?> msg){
        log.info("test-1消息内容：{}",msg);
    }

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @Scheduled(cron = "2/2 * * * * ?")
    public void sendKafka(){
        Gson gson = new GsonBuilder().create();
        Map<String,String> message = new HashMap<>();
        message.put("key",String.valueOf(System.currentTimeMillis()));
        message.put("id", UUID.randomUUID().toString());
        message.put("time",new Date().toString());
        log.info("+++++++++++++++++++++  message = {}", gson.toJson(message));
        kafkaTemplate.send("test-1", gson.toJson(message));
    }
}

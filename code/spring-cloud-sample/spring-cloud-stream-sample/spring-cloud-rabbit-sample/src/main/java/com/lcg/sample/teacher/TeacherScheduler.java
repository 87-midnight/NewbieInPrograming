package com.lcg.sample.teacher;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * @author linchuangang
 * @createTime 2020/11/1
 **/
@Component
public class TeacherScheduler {

    @Autowired
    private TeacherChatChannel teacherChatChannel;


    @Scheduled(cron = "0/5 * * * * ?")
    public void studentAChitchat(){
        Map info = new HashMap();
        info.put("role","teacher");
        info.put("words","what you guys are doing here");
        info.put("timestamp",System.currentTimeMillis());
        MessageBuilder<Map> builder = MessageBuilder.withPayload(info);
        Message message = builder.build();
        teacherChatChannel.teacherLesson().send(message);
    }
}

package com.lcg.sample.student;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.SubscribableChannel;

/**
 * @author linchuangang
 * @createTime 2020/10/31
 **/
public interface StudentChatChannel {

    @Input("classroom-input")
    MessageChannel studentLesson1();

    @Input("classroom-input1")
    MessageChannel studentLesson2();

    @Output("chitchat-output")
    MessageChannel studentA();

    @Input("chitchat-input")
    SubscribableChannel studentB();
}

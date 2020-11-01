package com.lcg.sample.teacher;

import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;

/**
 * @author linchuangang
 * @createTime 2020/10/31
 **/
public interface TeacherChatChannel {

    @Output("classroom-output")
    MessageChannel teacherLesson();
}

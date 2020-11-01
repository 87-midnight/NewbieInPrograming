package com.lcg.sample.start;

import com.lcg.sample.student.StudentConfigurer;
import com.lcg.sample.teacher.TeacherConfigurer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

/**
 * @author linchuangang
 * @createTime 2020/10/31
 **/
@SpringBootApplication
@Import(value = {
        StudentConfigurer.class,
        TeacherConfigurer.class
})
public class RabbitApplication {
    public static void main(String[] args) {
        SpringApplication.run(RabbitApplication.class,args);
    }
}

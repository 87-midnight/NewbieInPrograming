package com.lcg.sample;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

/**
 * @author linchuangang
 * @createTime 2020/10/21
 **/
@SpringBootApplication
public class KafkaBootApplication {

    public static void main(String[] args) {
        new SpringApplicationBuilder(KafkaBootApplication.class).web(WebApplicationType.NONE).run(args);
    }
}

package com.lcg.sample;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@MapperScan(value = "com.lcg.sample.mapper")
public class MybatisApplication implements CommandLineRunner {

    public static void main(String[]args){
        SpringApplication.run(MybatisApplication.class,args);
    }


    @Bean
    public ApplicationRunner xml(){
        return args->{

        };
    }

    @Override
    public void run(String... args) throws Exception {
    }
}

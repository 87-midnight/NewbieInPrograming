package com.lcg.sample;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.util.List;

/**
 * @author linchuangang
 * @create 2019-11-13 11:37
 **/
@SpringBootApplication
@Slf4j
public class SpringJdbcApplication {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public static void main(String...args){
        SpringApplication.run(SpringJdbcApplication.class,args);
    }

    @Bean
    public CommandLineRunner postgres(){
        return s->{
            List list = this.jdbcTemplate.queryForList("select * from user");
            log.info("{}",list);
        };
    }
}

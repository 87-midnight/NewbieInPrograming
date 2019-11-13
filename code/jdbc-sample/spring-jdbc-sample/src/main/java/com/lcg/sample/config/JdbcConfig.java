package com.lcg.sample.config;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceBuilder;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

/**
 * @author linchuangang
 * @create 2019-11-13 11:56
 **/
@Configuration
public class JdbcConfig {

    @Bean(name = "mysql")
    @ConfigurationProperties(prefix = "spring.datasource.first")
    public DataSource initMysql(){
        return DruidDataSourceBuilder.create().build();
    }

    @Bean(name = "postgres")
    @ConfigurationProperties(prefix = "spring.datasource.second")
    public DataSource initPostgres(){
        return DruidDataSourceBuilder.create().build();
    }

    @Bean(name = "mysqlTemplate")
    JdbcTemplate jdbcTemplateOne(@Qualifier("mysql") DataSource dsOne) {
        return new JdbcTemplate(dsOne);
    }

    @Bean(name = "postgresTemplate")
    JdbcTemplate jdbcTemplateTwo(@Qualifier("postgres") DataSource dsTwo) {
        return new JdbcTemplate(dsTwo);
    }
}

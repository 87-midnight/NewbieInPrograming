package com.lcg.sample.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceBuilder;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

/**
 * @author linchuangang
 * @create 2019-11-13 11:56
 **/
@Configuration
public class JdbcConfig {

    @Bean(name = "mysqlDataSource")
    @Primary
    @ConfigurationProperties(prefix = "spring.datasource.first")
    public DataSource initMysql(){
        return DruidDataSourceBuilder.create().build();
    }

    @Bean(name = "postgresDataSource")
    @ConfigurationProperties(prefix = "spring.datasource.second")
    public DataSource initPostgres(){
        DruidDataSource dataSource = DruidDataSourceBuilder.create().build();
        dataSource.setDriverClassName("org.postgresql.Driver");
        dataSource.setDbType("PostgreSqlDialect");
        return dataSource;
    }

    @Bean(name = "mysqlTemplate")
    JdbcTemplate jdbcTemplateOne(@Qualifier("mysqlDataSource") DataSource dsOne) {
        return new JdbcTemplate(dsOne);
    }

}

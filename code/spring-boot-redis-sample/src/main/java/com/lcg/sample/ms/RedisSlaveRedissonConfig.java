package com.lcg.sample.ms;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.client.RedisClient;
import org.redisson.config.Config;
import org.redisson.config.SingleServerConfig;
import org.redisson.spring.data.connection.RedissonConnectionFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * @author linchuangang
 * @createTime 2020/11/3
 **/
@Configuration
public class RedisSlaveRedissonConfig {

    @Bean
    public RedissonClient client(){
        Config config = new Config();
        SingleServerConfig serverConfig = config.useSingleServer().setAddress("redis://127.0.0.1:6379")
                .setTimeout(2000)
                .setPassword("123456")
                .setDatabase(1);
        return Redisson.create(config);
    }

    @Bean("redissonTemplate")
    public RedisTemplate<Object,Object> redisTemplateConfig(){
        RedisTemplate<Object,Object> redisTemplate = new RedisTemplate<>();
        RedisConnectionFactory factory = new RedissonConnectionFactory(client());
        redisTemplate.setConnectionFactory(factory);
        JdkSerializationRedisSerializer jdkSerializationRedisSerializer = new JdkSerializationRedisSerializer();
        StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
        redisTemplate.setDefaultSerializer(jdkSerializationRedisSerializer);
        redisTemplate.setKeySerializer(stringRedisSerializer);
        redisTemplate.setHashKeySerializer(stringRedisSerializer);
        redisTemplate.setHashValueSerializer(jdkSerializationRedisSerializer);
        return redisTemplate;
    }
}

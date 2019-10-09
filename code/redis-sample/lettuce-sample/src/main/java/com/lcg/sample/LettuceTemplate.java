package com.lcg.sample;

import io.lettuce.core.RedisClient;
import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.api.sync.RedisCommands;

import javax.annotation.PreDestroy;

public class LettuceTemplate {

    private StatefulRedisConnection<String,String> stringConnection;

    public LettuceTemplate getConnection(final String url,final Integer port,final String password){
        stringConnection = RedisClient.create("redis://"+password+"@"+url+":"+port).connect();
        return this;
    }
    public LettuceTemplate getConnection(final String url,final Integer port,final String password,final int database){
        stringConnection = RedisClient.create("redis://"+password+"@"+url+":"+port+"/"+database).connect();
        return this;
    }
    public LettuceTemplate getConnection(final String url,final Integer port,final int database){
        stringConnection = RedisClient.create("redis://"+url+":"+port+"/"+database).connect();
        return this;
    }
    public LettuceTemplate getConnection(final String url,final Integer port){
        stringConnection = RedisClient.create("redis://"+url+":"+port).connect();
        return this;
    }

    public LettuceTemplate set(final String key,final String value){
        RedisCommands<String,String> redisCommands = stringConnection.sync();
        redisCommands.set(key,value);
        return this;
    }

    public LettuceTemplate setHash(final String key,final String hKey,final String hValue,final long expire){
        RedisCommands<String,String> redisCommands = stringConnection.sync();
        redisCommands.hset(key,hKey,hValue);
        return this;
    }


    public void close(){
        if (stringConnection != null && stringConnection.isOpen()){
            stringConnection.close();
        }
    }
}

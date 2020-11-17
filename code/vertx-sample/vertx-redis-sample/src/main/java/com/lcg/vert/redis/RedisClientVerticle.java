package com.lcg.vert.redis;

import io.vertx.core.*;
import io.vertx.redis.client.Redis;
import io.vertx.redis.client.RedisAPI;
import io.vertx.redis.client.RedisConnection;
import io.vertx.redis.client.RedisOptions;
import io.vertx.redis.client.impl.RedisAPIImpl;

import java.util.Arrays;


/**
 * @author linchuangang
 * @createTime 2020/11/17
 **/
public class RedisClientVerticle extends AbstractVerticle {

    private RedisConnection client;
    public static final Vertx vertx = Vertx.vertx();

    @Override
    public void start(Future<Void> startFuture) throws Exception {
        Redis.createClient(vertx,new RedisOptions().addConnectionString("redis://localhost:6379")).connect(
                onConnect->{
                    if (onConnect.succeeded()){
                        client = onConnect.result();
                    }
                }
        );
        RedisAPI api = new RedisAPIImpl(client);
        api.set(Arrays.asList("sss",""),x->{});
    }

    public static void main(String[] args) throws Exception {

        vertx.deployVerticle(new RedisClientVerticle());
    }
}

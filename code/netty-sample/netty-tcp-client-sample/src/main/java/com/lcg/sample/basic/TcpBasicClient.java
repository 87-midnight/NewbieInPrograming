package com.lcg.sample.basic;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.bytes.ByteArrayDecoder;
import io.netty.handler.codec.bytes.ByteArrayEncoder;
import lombok.extern.slf4j.Slf4j;

import java.net.InetSocketAddress;

/**
 * @author linchuangang
 * @createTime 2020/11/1
 **/
@Slf4j
public class TcpBasicClient {

    private Integer serverPort;
    private String host;

    private Channel client;

    public TcpBasicClient(Integer serverPort, String host) {
        this.serverPort = serverPort;
        this.host = host;
    }

    public void startClient()throws Exception{
        EventLoopGroup group = new NioEventLoopGroup();
        Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(group).channel(NioSocketChannel.class);
        bootstrap.handler(new ChannelInitializer<Channel>() {
            @Override
            protected void initChannel(Channel ch) throws Exception{
                ch.pipeline().addLast(new ByteArrayDecoder());
                ch.pipeline().addLast(new ByteArrayEncoder());
                ch.pipeline().addLast(new BasicStringHandler());
            }
        });
        bootstrap.option(ChannelOption.SO_KEEPALIVE,true);
        ChannelFuture f = bootstrap.connect(new InetSocketAddress(host,serverPort)).sync();
        if (f.isSuccess()){
            client = f.channel();
            log.info("连接TCP服务器"+host+"成功");
            client.writeAndFlush("hello".getBytes());
        }
    }

    public void sendMsg(String msg){
        client.writeAndFlush((msg+"=>"+client.id().asLongText()).getBytes());
    }

}

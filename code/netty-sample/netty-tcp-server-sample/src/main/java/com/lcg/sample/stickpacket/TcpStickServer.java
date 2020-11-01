package com.lcg.sample.stickpacket;

import com.lcg.sample.basic.BasicMessageHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import lombok.extern.slf4j.Slf4j;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * @author linchuangang
 * @createTime 2020/11/2
 **/
@Slf4j
public class TcpStickServer {
    private Integer port;

    private Channel server;

    private EventLoopGroup boss;
    private EventLoopGroup group;

    public static ConcurrentMap<String,Channel> clients_s = new ConcurrentHashMap<>();

    public TcpStickServer(Integer port) {
        this.port = port;
    }

    public void startServer()throws Exception{
        ServerBootstrap sb = new ServerBootstrap();
        boss = new NioEventLoopGroup(5);
        group = new NioEventLoopGroup(5);
        sb.group(boss,group)
                .channel(NioServerSocketChannel.class)
                .option(ChannelOption.SO_REUSEADDR,true)
                .option(ChannelOption.SO_KEEPALIVE,true)
                .option(ChannelOption.SO_RCVBUF,1024*64)
                .option(ChannelOption.SO_SNDBUF,1024*64)
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                        ch.pipeline().addLast(new MessageStreamDecoder());
                        ch.pipeline().addLast(new MessageStreamEncoder());
                        ch.pipeline().addLast(new TcpStickServerHandler());
                    }
                });
        ChannelFuture f = sb.bind(new InetSocketAddress(port)).await();
        if (f.isSuccess()){
            server = f.channel();
            log.info("Tcp服务器启动成功");
        }
    }

    public void sendToAll(){
        for (Map.Entry<String,Channel>entry : clients_s.entrySet()){
            UserInfoMessage msg = new UserInfoMessage();
            msg.setContent("hello,client".getBytes(StandardCharsets.UTF_8));
            msg.setTimestamp(System.currentTimeMillis());
            msg.setLength(new Random().nextInt(9999));
            entry.getValue().writeAndFlush(msg);
        }
    }
}

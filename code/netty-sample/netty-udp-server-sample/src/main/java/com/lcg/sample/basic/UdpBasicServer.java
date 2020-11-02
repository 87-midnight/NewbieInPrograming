package com.lcg.sample.basic;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.DatagramPacket;
import io.netty.channel.socket.nio.NioDatagramChannel;
import lombok.extern.slf4j.Slf4j;

import java.net.InetSocketAddress;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author linchuangang
 * @createTime 2020/11/2
 **/
@Slf4j
public class UdpBasicServer {

    private int port;

    private EventLoopGroup group;
    private Channel parent;
    public static final Map<String,InetSocketAddress> clients_udp = new ConcurrentHashMap<>();

    public UdpBasicServer(int port) {
        this.port = port;
    }

    public void start() throws InterruptedException {
        group = new NioEventLoopGroup();
        Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(group)
                .channel(NioDatagramChannel.class)
                .option(ChannelOption.SO_RCVBUF, 1024*64)
                .option(ChannelOption.SO_SNDBUF, 1024*64)
                .handler(new ChannelInitializer<NioDatagramChannel>(){

                    @Override
                    protected void initChannel(NioDatagramChannel ch) throws Exception {
                        ch.pipeline().addLast(new UdpBasicServerHandler());
                    }
                });
        this.parent = bootstrap.bind(new InetSocketAddress(this.port)).sync().channel();
        if (this.parent.isActive()){
            log.info("udp服务端启动成功");
        }
    }

    public void sendMessage(){
        String msg = "你好，客户端。";
        clients_udp.forEach((k,v)->{
            ByteBuf byteBuf = Unpooled.wrappedBuffer(msg.getBytes());
            DatagramPacket packet = new DatagramPacket(byteBuf, v);
            parent.writeAndFlush(packet);
        });
    }

    public void stop(){
        if (parent.isOpen()){
            group.shutdownGracefully();
            parent.closeFuture().awaitUninterruptibly();
        }
    }
}

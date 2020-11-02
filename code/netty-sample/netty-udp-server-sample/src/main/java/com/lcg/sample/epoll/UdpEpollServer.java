package com.lcg.sample.epoll;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.epoll.EpollDatagramChannel;
import io.netty.channel.epoll.EpollEventLoopGroup;
import io.netty.channel.epoll.EpollSocketChannel;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.DatagramPacket;
import lombok.extern.slf4j.Slf4j;

import java.net.InetSocketAddress;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author linchuangang
 * @createTime 2020/11/2
 **/
@Slf4j
public class UdpEpollServer {

    private int port;
    private Channel parent;
    private EventLoopGroup boss;

    public static final Map<String,InetSocketAddress> clients_epoll = new ConcurrentHashMap<>();

    public UdpEpollServer(int port){
        this.port = port;
        checkSystem();
    }

    private void checkSystem(){
        String name = System.getProperty("os.name");
//        if (!name.equalsIgnoreCase("Linux")){
//            throw new UnsupportedOperationException("unsupported system");
//        }
    }

    public void start()throws Exception{
        boss = new EpollEventLoopGroup(5);
        Bootstrap server = new Bootstrap();
        server.group(boss)
                .channel(EpollDatagramChannel.class)
                .option(ChannelOption.SO_RCVBUF, 1024*64)
                .option(ChannelOption.SO_SNDBUF, 1024*64)
                .option(ChannelOption.SO_REUSEADDR,true)
                .handler(new ChannelInitializer<EpollDatagramChannel>() {
                    @Override
                    protected void initChannel(EpollDatagramChannel ch) throws Exception {
                        ch.pipeline().addLast(new UdpEpollHandler());
                    }
                });
        Channel channel = server.bind(new InetSocketAddress(port)).sync().channel();
        if (channel.isActive()){
            parent = channel;
            log.info("udp epoll服务端启动成功");
        }
    }
    public void sendMessage(){
        String msg = "你好，客户端。";
        clients_epoll.forEach((k,v)->{
            ByteBuf byteBuf = Unpooled.wrappedBuffer(msg.getBytes());
            DatagramPacket packet = new DatagramPacket(byteBuf, v);
            parent.writeAndFlush(packet);
        });
    }
}

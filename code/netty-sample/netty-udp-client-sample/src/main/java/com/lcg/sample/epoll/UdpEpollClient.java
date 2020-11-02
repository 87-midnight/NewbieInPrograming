package com.lcg.sample.epoll;

import com.lcg.sample.basic.UdpBasicClientHandler;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.epoll.EpollDatagramChannel;
import io.netty.channel.epoll.EpollEventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.DatagramPacket;
import io.netty.channel.socket.nio.NioDatagramChannel;

import java.net.InetSocketAddress;

/**
 * @author linchuangang
 * @createTime 2020/11/2
 **/
public class UdpEpollClient {

    private int port;
    private String host = "localhost";

    private EventLoopGroup group;
    private Channel parent;

    public UdpEpollClient(int port) {
        this.port = port;
    }

    public UdpEpollClient(int port, String host) {
        this.port = port;
        this.host = host;
    }

    public void start() throws InterruptedException {
        group = new EpollEventLoopGroup(5);
        Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(group)
                .channel(EpollDatagramChannel.class)
                .option(ChannelOption.SO_RCVBUF, 1024*64)
                .option(ChannelOption.SO_SNDBUF, 1024*64)
                .handler(new ChannelInitializer<EpollDatagramChannel>(){

                    @Override
                    protected void initChannel(EpollDatagramChannel ch) throws Exception {
                        ch.pipeline().addLast(new UdpEpollClientHandler());
                    }
                });
        this.parent = bootstrap.connect(new InetSocketAddress("localhost",this.port)).sync().channel();
    }

    public void sendMessage(){
        String msg = "你好，服务端。";
        ByteBuf byteBuf = Unpooled.wrappedBuffer(msg.getBytes());
        DatagramPacket packet = new DatagramPacket(byteBuf, (InetSocketAddress) parent.remoteAddress());
        parent.writeAndFlush(packet);

    }

    public void stop(){
        if (parent.isOpen()){
            group.shutdownGracefully();
            parent.closeFuture().awaitUninterruptibly();
        }
    }
}

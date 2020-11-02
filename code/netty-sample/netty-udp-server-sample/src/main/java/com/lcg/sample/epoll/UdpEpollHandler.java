package com.lcg.sample.epoll;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.socket.DatagramPacket;
import lombok.extern.slf4j.Slf4j;

import static com.lcg.sample.epoll.UdpEpollServer.clients_epoll;


/**
 * @author linchuangang
 * @createTime 2020/11/2
 **/
@Slf4j
@ChannelHandler.Sharable
public class UdpEpollHandler extends SimpleChannelInboundHandler<DatagramPacket> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, DatagramPacket msg) throws Exception {
        ByteBuf buf = msg.content();
        int len = buf.readableBytes();
        byte[] data = new byte[len];
        buf.readBytes(data);
        String receive = new String(data,"UTF-8");
        clients_epoll.put(msg.sender().toString(),  msg.sender());
        log.info("收到客户端的消息：【{}】",receive);
        log.info("clients:{}",clients_epoll);
    }

}
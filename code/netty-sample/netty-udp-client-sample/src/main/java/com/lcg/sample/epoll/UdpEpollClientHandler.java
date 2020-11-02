package com.lcg.sample.epoll;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.socket.DatagramPacket;
import lombok.extern.slf4j.Slf4j;

/**
 * @author linchuangang
 * @createTime 2020/11/2
 **/
@ChannelHandler.Sharable
@Slf4j
public class UdpEpollClientHandler extends SimpleChannelInboundHandler<DatagramPacket> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, DatagramPacket msg) throws Exception {
        ByteBuf buf = msg.content();
        int len = buf.readableBytes();
        byte[] data = new byte[len];
        buf.readBytes(data);
        String receive = new String(data,"UTF-8");
        log.info("收到服务端的消息：【{}】",receive);
    }
}

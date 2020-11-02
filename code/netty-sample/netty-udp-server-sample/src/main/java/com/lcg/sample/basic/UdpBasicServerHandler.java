package com.lcg.sample.basic;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.socket.DatagramPacket;
import lombok.extern.slf4j.Slf4j;

import java.net.InetSocketAddress;

import static com.lcg.sample.basic.UdpBasicServer.clients_udp;

/**
 * @author linchuangang
 * @createTime 2020/11/2
 **/
@ChannelHandler.Sharable
@Slf4j
public class UdpBasicServerHandler extends SimpleChannelInboundHandler<DatagramPacket> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, DatagramPacket msg) throws Exception {
        ByteBuf buf = msg.content();
        int len = buf.readableBytes();
        byte[] data = new byte[len];
        buf.readBytes(data);
        String receive = new String(data,"UTF-8");
        clients_udp.put(msg.sender().toString(),  msg.sender());
        log.info("收到客户端的消息：【{}】",receive);
        log.info("clients:{}",clients_udp);
    }

}

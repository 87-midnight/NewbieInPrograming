package com.lcg.sample.stickpacket;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

import static com.lcg.sample.stickpacket.TcpStickServer.clients_s;

/**
 * @author linchuangang
 * @createTime 2020/11/2
 **/
@Slf4j
public class TcpStickServerHandler extends SimpleChannelInboundHandler<UserInfoMessage> {
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        clients_s.putIfAbsent(ctx.channel().id().asLongText(),ctx.channel());
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        clients_s.remove(ctx.channel().id().asLongText());
    }
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, UserInfoMessage msg) throws Exception {
        log.info("收到客户端的消息：【{}】",msg);
    }
}

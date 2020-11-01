package com.lcg.sample.basic;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

import static com.lcg.sample.basic.TcpBasicServer.clients;

/**
 * @author linchuangang
 * @createTime 2020/11/1
 **/
@Slf4j
public class BasicMessageHandler extends SimpleChannelInboundHandler<byte[]> {
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, byte[] bytes) throws Exception {
        log.info("收到客户端["+channelHandlerContext.channel().id()+"]发给我的消息：【"+new String(bytes)+"】");
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        clients.putIfAbsent(ctx.channel().id().asLongText(),ctx.channel());
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        clients.remove(ctx.channel().id().asLongText());
    }

    @Override
    public boolean isSharable() {
        return true;
    }
}

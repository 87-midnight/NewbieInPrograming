package com.lcg.sample.stickpacket;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

/**
 * @author linchuangang
 * @createTime 2020/11/2
 **/
@Slf4j
public class TcpStickClientHandler extends SimpleChannelInboundHandler<UserInfoMessage> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, UserInfoMessage msg) throws Exception {
        log.info("收到服务端的消息：[{}]",msg);
    }
}

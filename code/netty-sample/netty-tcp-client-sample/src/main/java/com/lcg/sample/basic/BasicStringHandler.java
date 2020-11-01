package com.lcg.sample.basic;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

/**
 * @author linchuangang
 * @createTime 2020/11/1
 **/
@Slf4j
public class BasicStringHandler extends ChannelInboundHandlerAdapter {


    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        log.info("收到服务器发给我的消息："+new String((byte[])msg));
    }
}

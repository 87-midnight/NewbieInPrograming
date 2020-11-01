package com.lcg.sample;

import com.lcg.sample.basic.TcpBasicServer;

import java.nio.charset.StandardCharsets;

/**
 * @author linchuangang
 * @createTime 2020/11/1
 **/
public class TcpServerTest {
    public static void main(String[] args){
        TcpBasicServer basicServer = new TcpBasicServer(8875);
        new Thread(()->{

            try {
                basicServer.startServer();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();

        while (true) {
            TcpBasicServer.clients.forEach((k, v) -> {
                v.writeAndFlush(("你好，客户端：" + v.id().asShortText() + ",时间：" + System.currentTimeMillis())
                        .getBytes(StandardCharsets.UTF_8));
            });
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

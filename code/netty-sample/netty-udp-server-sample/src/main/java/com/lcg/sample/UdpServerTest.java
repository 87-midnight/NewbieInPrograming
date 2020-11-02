package com.lcg.sample;

import com.lcg.sample.basic.UdpBasicServer;
import com.lcg.sample.epoll.UdpEpollServer;

/**
 * @author linchuangang
 * @createTime 2020/11/2
 **/
public class UdpServerTest {
    public static void main(String[] args) throws Exception{
//        basic();
        epoll();
    }

    private static void epoll()throws Exception{
        UdpEpollServer basicServer = new UdpEpollServer(8754);
        basicServer.start();
        new Thread(()->{
            while (true){
                try {
                    basicServer.sendMessage();
                    Thread.sleep(3000);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        }).start();
    }

    private static void basic()throws Exception{
        UdpBasicServer basicServer = new UdpBasicServer(8754);
        basicServer.start();
        new Thread(()->{
            while (true){

                try {
                    System.out.println("xxx");
                    basicServer.sendMessage();
                    Thread.sleep(3000);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        }).start();
    }
}

package com.lcg.sample;

import com.lcg.sample.basic.UdpBasicClient;
import com.lcg.sample.epoll.UdpEpollClient;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author linchuangang
 * @createTime 2020/11/2
 **/
public class UdpClientTest {

    public static void main(String[] args) throws Exception{
//        basic();
        epollClients();
    }

    private static void epollClients(){
        Thread thread = new Thread(()->{
            try {
                UdpEpollClient basicClient = new UdpEpollClient(8754);
                basicClient.start();
                while (true) {
                    basicClient.sendMessage();
                    Thread.sleep(4000);
                }
            }catch (Exception var1){

            }
        });
        ExecutorService executorService = Executors.newFixedThreadPool(50);
        for (int i=0; i<1000;i++){
            executorService.submit(thread);
        }
    }
    private static void basic()throws Exception{
        UdpBasicClient basicClient = new UdpBasicClient(8754);
        basicClient.start();
        while (true) {
            basicClient.sendMessage();
            Thread.sleep(4000);
        }
    }
}

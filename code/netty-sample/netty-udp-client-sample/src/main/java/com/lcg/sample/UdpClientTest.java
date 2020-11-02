package com.lcg.sample;

import com.lcg.sample.basic.UdpBasicClient;

/**
 * @author linchuangang
 * @createTime 2020/11/2
 **/
public class UdpClientTest {

    public static void main(String[] args) throws Exception{
        basic();
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

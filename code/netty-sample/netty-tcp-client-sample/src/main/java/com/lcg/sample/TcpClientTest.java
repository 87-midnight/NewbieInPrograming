package com.lcg.sample;

import com.lcg.sample.basic.TcpBasicClient;
import lombok.extern.slf4j.Slf4j;

/**
 * @author linchuangang
 * @createTime 2020/11/1
 **/
@Slf4j
public class TcpClientTest {

    public static void main(String[] args) throws Exception {
        TcpBasicClient basicClient = new TcpBasicClient(8875,"127.0.0.1");
        basicClient.startClient();
        new Thread(()->{
            while (true){
                basicClient.sendMsg(("服务器你好，本地时间为："+System.currentTimeMillis()));
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        }).start();
    }
}

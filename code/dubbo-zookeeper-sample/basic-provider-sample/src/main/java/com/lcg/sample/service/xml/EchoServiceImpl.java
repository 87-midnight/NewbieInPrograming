package com.lcg.sample.service.xml;

import com.lcg.sample.service.EchoService;
import org.apache.dubbo.rpc.RpcContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EchoServiceImpl implements EchoService {

    private Logger log = LoggerFactory.getLogger(this.getClass());

    public String print(String msg) {
        log.warn("this is provider service print:{}",msg);
        return "Hello " + msg + ", response from provider: " + RpcContext.getContext().getLocalAddress();
    }
}

package com.lcg.sample.service.annotation;

import com.lcg.sample.service.EchoService;
import org.apache.dubbo.config.annotation.Service;
import org.apache.dubbo.rpc.RpcContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class EchoServiceAnnotation implements EchoService {

    private Logger log = LoggerFactory.getLogger(this.getClass());
    @Override
    public String print(String msg) {
        log.info("this is annotation provider service print:{}",msg);
        return "Hello " + msg + ", response from annotation provider: " + RpcContext.getContext().getLocalAddress();
    }
}

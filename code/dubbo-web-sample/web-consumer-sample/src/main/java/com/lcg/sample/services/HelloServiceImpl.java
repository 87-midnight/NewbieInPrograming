package com.lcg.sample.services;

import org.apache.dubbo.config.annotation.Service;

@Service
public class HelloServiceImpl implements HelloService {
    @Override
    public String sayHello(String msg) {
        return "hello,"+msg;
    }
}

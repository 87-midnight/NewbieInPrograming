package com.lcg.sample.web;

import org.apache.dubbo.config.ReferenceConfig;
import org.apache.dubbo.rpc.service.GenericService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @Autowired
    private ReferenceConfig<GenericService> referenceConfig;

    @GetMapping(value = "/echo")
    public Object echo(){
        referenceConfig.setInterface("com.lcg.sample.services.HelloService");
        GenericService helloService = referenceConfig.get();
        return helloService.$invoke("sayHello",new String[]{"java.lang.String"},new Object[]{"consumer"});
    }
}

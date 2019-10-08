package com.lcg.sample.web;

import org.apache.dubbo.config.ApplicationConfig;
import org.apache.dubbo.config.ReferenceConfig;
import org.apache.dubbo.config.RegistryConfig;
import org.apache.dubbo.config.context.ConfigManager;
import org.apache.dubbo.rpc.service.GenericService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @Autowired
    ReferenceConfig<GenericService> referenceConfig;
    @Autowired
    RegistryConfig registryConfig;
    @GetMapping(value = "/getUser")
    public Object test(){
//        ApplicationConfig applicationConfig = ConfigManager.getInstance().getApplication().get();
//        applicationConfig.setRegistry(registryConfig);
//        referenceConfig.setApplication(applicationConfig);
        referenceConfig.setInterface("com.lcg.sample.service.UserService");
        GenericService userService = referenceConfig.get();
        return userService.$invoke("getList",new String[]{"java.lang.String"},new Object[]{"lcg"});
    }
}

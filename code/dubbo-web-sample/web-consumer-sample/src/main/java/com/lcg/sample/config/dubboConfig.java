package com.lcg.sample.config;


import org.apache.dubbo.config.ApplicationConfig;
import org.apache.dubbo.config.ReferenceConfig;
import org.apache.dubbo.config.RegistryConfig;
import org.apache.dubbo.config.context.ConfigManager;
import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.apache.dubbo.rpc.service.GenericService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @author linchuangang
 * @create 2019-09-27 6:24 PM
 **/
@Configuration
@EnableDubbo(scanBasePackages = {"com.lcg.sample.service"})
@EnableScheduling
public class dubboConfig {

//    @Bean
//    public ReferenceConfig<GenericService> config(){
//        ReferenceConfig<GenericService> referenceConfig = new ReferenceConfig<>();
//        ApplicationConfig applicationConfig = ConfigManager.getInstance().getApplication().get();
//        applicationConfig.setRegistry(registryConfig());
//        referenceConfig.setApplication(applicationConfig);
//        referenceConfig.setGeneric(true);
//        referenceConfig.setAsync(false);
//        referenceConfig.setTimeout(7000);
//        return referenceConfig;
//    }

    @Bean
    public RegistryConfig registryConfig(){
        RegistryConfig registryConfig = new RegistryConfig();
        registryConfig.setAddress("zookeeper://127.0.0.1:2181");
        return registryConfig;
    }
}

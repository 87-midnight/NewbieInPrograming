package com.lcg.sample.config;

import org.apache.dubbo.config.ApplicationConfig;
import org.apache.dubbo.config.ReferenceConfig;
import org.apache.dubbo.config.RegistryConfig;
import org.apache.dubbo.config.context.ConfigManager;
import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.apache.dubbo.rpc.service.GenericService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;


/**
 * @author linchuangang
 * @create 2019-09-27 6:24 PM
 **/
@Configuration
@EnableDubbo(scanBasePackages = {"com.lcg.sample.service"})
@PropertySource("classpath:dubbo-service1.properties")
@EnableScheduling
public class ServiceNodeConfig {

    private Logger log = LoggerFactory.getLogger(this.getClass());


    @Scheduled(fixedDelay = 3000)
    public void begin(){
        log.info("service1 schedule task is beginning.");
        RegistryConfig registryConfig = new RegistryConfig();
        registryConfig.setAddress("zookeeper://127.0.0.1:2181");
        ReferenceConfig<GenericService> referenceConfig = new ReferenceConfig<>();
        referenceConfig.setInterface("com.lcg.sample.service.ClassService");
        ApplicationConfig applicationConfig = ConfigManager.getInstance().getApplication().get();
        applicationConfig.setRegistry(registryConfig);
        referenceConfig.setApplication(applicationConfig);
        referenceConfig.setGeneric(true);
        referenceConfig.setAsync(false);
        referenceConfig.setTimeout(7000);

        GenericService genericService = referenceConfig.get();
        Object result = genericService.$invoke("saveClass",new String[]{"java.lang.Integer"},new Object[]{2});
        log.info("invoke service2 application's component[ClassService] method saveClass result:{}",result);
    }
}

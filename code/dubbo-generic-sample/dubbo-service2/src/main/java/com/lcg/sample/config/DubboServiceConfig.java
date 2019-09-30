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

@Configuration
@EnableDubbo(scanBasePackages = {"com.lcg.sample.service"})
@PropertySource("classpath:dubbo-service2.properties")
@EnableScheduling
public class DubboServiceConfig {

    private Logger log = LoggerFactory.getLogger(this.getClass());

    @Scheduled(fixedDelay = 2000)
    public void start(){
        log.info("schedule task is running.");
        RegistryConfig registryConfig = new RegistryConfig();
        registryConfig.setAddress("zookeeper://127.0.0.1:2181");
        ReferenceConfig<GenericService> referenceConfig = new ReferenceConfig<>();
        referenceConfig.setInterface("com.lcg.sample.service.UserService");
        ApplicationConfig applicationConfig = ConfigManager.getInstance().getApplication().get();
        applicationConfig.setRegistry(registryConfig);
        referenceConfig.setApplication(applicationConfig);
        referenceConfig.setGeneric(true);
        referenceConfig.setAsync(false);
        referenceConfig.setTimeout(7000);

        GenericService genericService = referenceConfig.get();
        Object result = genericService.$invoke("save",new String[]{},new Object[]{});
        log.info("invoke service1 application's component[UserService] method [save] result:{}",result);
    }
}

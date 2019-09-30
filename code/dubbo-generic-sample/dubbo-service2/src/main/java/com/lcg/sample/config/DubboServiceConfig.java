package com.lcg.sample.config;

import com.lcg.sample.service.CommonService;
import org.apache.dubbo.config.ApplicationConfig;
import org.apache.dubbo.config.ReferenceConfig;
import org.apache.dubbo.config.RegistryConfig;
import org.apache.dubbo.config.annotation.Reference;
import org.apache.dubbo.config.context.ConfigManager;
import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.apache.dubbo.rpc.service.GenericService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.Random;

@Configuration
@EnableDubbo(scanBasePackages = {"com.lcg.sample.service"})
@PropertySource("classpath:dubbo-service2.properties")
@EnableScheduling
public class DubboServiceConfig {

    private Logger log = LoggerFactory.getLogger(this.getClass());

    @Reference(check = false)
    private CommonService commonService;

    @Scheduled(fixedDelay = 5000)
    public void test(){
        log.info("泛化实现的调用print方法的结果：{}",commonService.print("hello,generic implements!",new Random().nextInt(9999)));
    }

    @Scheduled(fixedDelay = 2000)
    public void start(){
        log.info("schedule task is running.");
        RegistryConfig registryConfig = new RegistryConfig();
        registryConfig.setAddress("zookeeper://127.0.0.1:2181");
        // 该实例很重量，里面封装了所有与注册中心及服务提供方连接，请看缓存部分
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

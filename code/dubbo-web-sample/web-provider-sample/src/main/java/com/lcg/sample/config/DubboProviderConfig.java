package com.lcg.sample.config;

import org.apache.dubbo.config.ApplicationConfig;
import org.apache.dubbo.config.ProtocolConfig;
import org.apache.dubbo.config.ReferenceConfig;
import org.apache.dubbo.config.RegistryConfig;
import org.apache.dubbo.config.spring.context.annotation.DubboComponentScan;
import org.apache.dubbo.rpc.service.GenericService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@DubboComponentScan(basePackages = "com.lcg.sample.service.impl")
@Configuration
public class DubboProviderConfig {

    @Bean
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }

    @Bean
    public ApplicationConfig applicationConfig(){
        ApplicationConfig applicationConfig = new ApplicationConfig();
        applicationConfig.setName("dubbo-web-provider");
        applicationConfig.setRegistry(registryConfig());
        applicationConfig.setQosPort(8471);
        return applicationConfig;
    }

    @Bean
    public ReferenceConfig<GenericService> config(){
        ReferenceConfig<GenericService> referenceConfig = new ReferenceConfig<>();
        referenceConfig.setApplication(applicationConfig());
        referenceConfig.setGeneric(true);
        referenceConfig.setAsync(false);
        referenceConfig.setTimeout(7000);
        return referenceConfig;
    }

    @Bean
    public RegistryConfig registryConfig(){
        RegistryConfig registryConfig = new RegistryConfig();
        registryConfig.setAddress("zookeeper://127.0.0.1:2181");
        registryConfig.setProtocol("dubbo");
        registryConfig.setPort(5059);
        return registryConfig;
    }

    @Bean
    public ProtocolConfig protocolConfig(){
        ProtocolConfig config = new ProtocolConfig();
        config.setPort(5587);
        return config;
    }
}

### dubbo结合SpringMVC使用示例

>- 以web容器注解方式启动
>   - SpringMVC方式
>   - [dubbo-web-sample](../../../../code/dubbo-web-sample)

**基础SpringMVC配置使用在这不展开**

dubbo注册服务，暴露service的关键配置代码：

```java
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
        return registryConfig;
    }

    @Bean
    public ProtocolConfig protocolConfig(){
        ProtocolConfig config = new ProtocolConfig();
        config.setPort(5587);
        return config;
    }
}
```

>- DubboComponentScan注解扫描服务所在包
>- applicationConfig()定义一个dubbo应用的bean
>- config()泛化引用所需要的实例，以bean的方式生成单例
>- registryConfig()注册zookeeper，设置地址和通讯协议
>- protocolConfig()设置netty启动端口

***泛化引用示例***

```cmd
@Autowired
private ReferenceConfig<GenericService> referenceConfig;
//自动注入

//设置要访问的的服务类
referenceConfig.setInterface("com.lcg.sample.services.HelloService");
GenericService helloService = referenceConfig.get();
//调用的方法和参数传递
return helloService.$invoke("sayHello",new String[]{"java.lang.String"},new Object[]{"consumer"});
```

### dubbo结合spring-boot使用示例

示例请查看[spring-boot-dubbo-zk-example](https://github.com/87-midnight/spring-boot-example/tree/master/spring-boot-dubbo-zk-example)

### dubbo结合spring-boot、nacos使用示例

示例请查看[spring-boot-dubbo-nacos-example](https://github.com/87-midnight/spring-boot-example/tree/master/spring-boot-dubbo-nacos-example)
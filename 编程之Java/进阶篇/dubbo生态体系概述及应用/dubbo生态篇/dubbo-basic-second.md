### dubbo 泛化引用、泛化实现

**官方解释：**

>泛化接口调用方式主要用于客户端没有 API 接口及模型类元的情况，参数及返回值中的所有 POJO 均用 Map 表示，通常用于框架集成，
比如：实现一个通用的服务测试框架，可通过 GenericService 调用所有服务实现。

>泛接口实现方式主要用于服务器端没有API接口及模型类元的情况，参数及返回值中的所有POJO均用Map表示，通常用于框架集成，
比如：实现一个通用的远程服务Mock框架，可通过实现GenericService接口处理所有服务请求。

**示例代码：**

- [dubbo-service1](../../../../code/dubbo-generic-sample/dubbo-service1)
    - 分布式服务1，弱化了隶属提供方或消费方
- [dubbo-service2](../../../../code/dubbo-generic-sample/dubbo-service2)
    - 分布式服务2，弱化了隶属提供方或消费方
    
###### 泛化引用实现步骤

关键代码：

```java
        ReferenceConfig<GenericService> referenceConfig = new ReferenceConfig<>();
        referenceConfig.setInterface("com.lcg.sample.service.ClassService");
        ApplicationConfig applicationConfig = ConfigManager.getInstance().getApplication().get();
        applicationConfig.setRegistry(registryConfig);
        referenceConfig.setApplication(applicationConfig);
        referenceConfig.setGeneric(true);
        referenceConfig.setAsync(false);//不异步调用，设为true时，目标方法需要实现异步返回
        referenceConfig.setTimeout(7000);

        GenericService genericService = referenceConfig.get();
        Object result = genericService.$invoke("saveClass",new String[]{"java.lang.Integer"},new Object[]{2});
        log.info("invoke service2 application's component[ClassService] method saveClass result:{}",result);
```
>- 该代码位于dubbo-service1的ServiceNodeConfig类下，意在通过泛化引用的方式调用dubbo-service2的ClassService类方法。
>- 同理，该段代码同样适用于dubbo-service2里，在service2里使用该代码，可以调用dubbo-service1里任意一个service方法。
>   - 请看DubboServiceConfig类的start方法示例。

###### 泛化实现步骤

- 某个类实现了GenericService接口
    - 在$invoke方法里按照method参数来分类实现
    - 使用ServiceConfig配置且导出该类
- 调用方定义一个通用接口，定义的方法一般需在method范围内。
    - 使用@Reference注解，不检查，生成动态代理类
    - 调用接口的方法，实现远程$invoke方法的调用
    
关键代码：

- dubbo-service1实现GenericService接口
```java
public class CommonServiceImpl implements GenericService {
    @Override
    public Object $invoke(String s, String[] strings, Object[] objects) throws GenericException {
        if (s.equals("print")) {
            StringBuilder builder = new StringBuilder();
            builder.append("the parameter list:[");
            for (Object obj : objects){
                builder.append(obj);
            }
            builder.append("]");
            return builder.toString();
        }
        return 0;
    }
}
```

- dubbo-service1配置并导出
```java
        GenericService commonService = new CommonServiceImpl();
        ServiceConfig<GenericService> service = new ServiceConfig<>();
        ApplicationConfig applicationConfig = ConfigManager.getInstance().getApplication().get();
        service.setApplication(applicationConfig);
        service.setRegistry(registryConfig);
        service.setInterface("com.lcg.sample.service.CommonService");
        service.setRef(commonService);
        service.setGeneric("true");
        service.export();
```

- dubbo-service2定义通用接口

```java
public interface CommonService {
    String print(String echo,Integer random);
}
```

- dubbo-service2调用
    - 关键的注解@Reference加上
```java
    @Reference(check = false)
    private CommonService commonService;

    @Scheduled(fixedDelay = 5000)
    public void test(){
        log.info("泛化实现的调用print方法的结果：{}",commonService.print("hello,generic implements!",new Random().nextInt(9999)));
    }
```

### dubbo 泛化引用、实现缓存

#### dubbo泛化引用缓存方案

>缘由：ReferenceConfig笨重，每次都实例化会造成不必要的开销，甚至影响性能

**缓存方案：**

>- 使用官方的ReferenceConfigCache工具类

#### dubbo泛化实现缓存方案

>缘由：ServiceConfig笨重，每次都实例化会造成不必要的开销，甚至影响性能

**缓存方案：**

>- 自定义工具类，使用concurrentMap存储

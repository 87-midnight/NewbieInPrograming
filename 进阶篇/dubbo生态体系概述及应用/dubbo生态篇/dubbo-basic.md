### dubbo模块示例代码说明

- [dubbo-zookeeper-sample](../../../code/dubbo-zookeeper-sample)
    - 大部分的功能属性示例
    - [basic-consumer-sample](../../../code/dubbo-zookeeper-sample/basic-consumer-sample)
        - 下文若无特殊说明，均在此示例中
    - [basic-provider-sample](../../../code/dubbo-zookeeper-sample/basic-provider-sample) 
        - 下文若无特殊说明，均在此示例中
    - [embedded-zookeeper-server](../../../code/dubbo-zookeeper-sample/embedded-zookeeper-server)
        - 一个内置的zookeeper服务，以代码方式启动服务，不依赖独立的zookeeper组件
- [dubbo-nacos-sample](../../../code/dubbo-zookeeper-sample)
    - 展示了使用nacos为服务治理中心的示例


### dubbo配置及启动方式

**xml配置方式**

*服务提供者配置：*

```xml
<beans xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:dubbo="http://dubbo.apache.org/schema/dubbo"
       xmlns="http://www.springframework.org/schema/beans"
       xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd
       http://dubbo.apache.org/schema/dubbo http://dubbo.apache.org/schema/dubbo/dubbo.xsd">
    <dubbo:application name="demo-provider"/>
    <dubbo:registry address="zookeeper://127.0.0.1:2181"/>
    <dubbo:protocol name="dubbo" port="20890"/>
    <bean id="demoService" class="org.apache.dubbo.samples.basic.impl.DemoServiceImpl"/>
    <dubbo:service interface="org.apache.dubbo.samples.basic.api.DemoService" ref="demoService"/>
</beans>
```

*服务消费者配置：*

```xml
<beans xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:dubbo="http://dubbo.apache.org/schema/dubbo"
       xmlns="http://www.springframework.org/schema/beans"
       xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd
       http://dubbo.apache.org/schema/dubbo http://dubbo.apache.org/schema/dubbo/dubbo.xsd">
    <dubbo:application name="demo-consumer"/>
    <dubbo:registry group="aaa" address="zookeeper://127.0.0.1:2181"/>
    <dubbo:reference id="demoService" check="false" interface="org.apache.dubbo.samples.basic.api.DemoService"/>
</beans>
```

***xml配置文件方式启动程序：***

核心代码：
```cmd
ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("spring/dubbo-demo-consumer.xml");
context.start();
```

加载dubbo配置文件，启动程序。

**注解+属性文件方式**

关键属性配置：

```properties
dubbo.registry.address=zookeeper://${zookeeper.address:localhost}:2181
dubbo.consumer.timeout=3000
dubbo.protocol.name=dubbo
dubbo.protocol.port=20880
```

***启动服务，核心代码：***

```java
public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(ConsumerConfiguration.class);
        context.start();
        GreetingServiceConsumer greetingServiceConsumer = context.getBean(GreetingServiceConsumer.class);
        String hello = greetingServiceConsumer.doSayHello("zookeeper");
        System.out.println("result: " + hello);
    }

    @Configuration
    @EnableDubbo(scanBasePackages = "org.apache.dubbo.samples.action")
    @PropertySource("classpath:/spring/dubbo-consumer.properties")
    @ComponentScan(value = {"org.apache.dubbo.samples.action"})
    static class ConsumerConfiguration {

    }
```

### dubbo schema详细说明


### dubbo注解详细说明

### dubbo常见类的描述和使用

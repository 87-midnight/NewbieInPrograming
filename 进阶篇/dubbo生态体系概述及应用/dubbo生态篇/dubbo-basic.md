### dubbo模块示例代码说明

- [dubbo-zookeeper-sample](../../../code/dubbo-zookeeper-sample)
    - 大部分的功能属性示例
    - [basic-consumer-sample](../../../code/dubbo-zookeeper-sample/basic-consumer-sample)
        - 下文若无特殊说明，均在此示例中
    - [basic-provider-sample](../../../code/dubbo-zookeeper-sample/basic-provider-sample) 
        - 下文若无特殊说明，均在此示例中
    - [embedded-zookeeper-server](../../../code/dubbo-zookeeper-sample/embedded-zookeeper-server)
        - 一个内置的zookeeper服务，以代码方式启动服务，不依赖独立的zookeeper组件
- [dubbo-spring-boot-sample](../../../code/dubbo-zookeeper-sample)
    - 展示了使用以spring-boot为基础框架扩展的示例


### dubbo配置及启动方式

>- 非web容器xml方式启动
>   - [消费方xml方式](../../../code/dubbo-zookeeper-sample/basic-consumer-sample)
>   - [提供方xml方式](../../../code/dubbo-zookeeper-sample/basic-provider-sample)
>- 非web容器注解方式启动
>   - [消费方注解方式](../../../code/dubbo-zookeeper-sample/basic-consumer-sample)
>   - [提供方注解方式](../../../code/dubbo-zookeeper-sample/basic-provider-sample)


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
> 随着微服务架构的广泛地推广和实施。在 Java 生态系统中，以 Spring Boot 和 Spring Cloud 为代表的微服务框架，引入了全新的编程模型，包括：
>- 注解驱动（Annotation-Driven）
>- 外部化配置（External Configuration）
>- 以及自动装配（Auto-Configure）

####@EnableDubbo

>@EnableDubbo 注解是 @EnableDubboConfig 和 @DubboComponentScan两者组合的便捷表达方式。与注解驱动相关的是 @DubboComponentScan

通过 @EnableDubbo 可以在指定的包名下（通过 scanBasePackages），或者指定的类中（通过 scanBasePackageClasses）扫描 Dubbo 的服务提供者（以 @Service 标注）以及 Dubbo 的服务消费者（以 Reference 标注）。

扫描到 Dubbo 的服务提供方和消费者之后，对其做相应的组装并初始化，并最终完成服务暴露或者引用的工作。

**注：如果不使用外部化配置（External Configuration）的话，也可以直接使用 @DubboComponentScan。**

####@Service

>用来配置 Dubbo 的服务提供方

    单个注解使用时，@Service 只能定义在一个类上，表示一个服务的具体实现

**@Service注解附带属性介绍**

>- interfaceClass：指定服务提供方实现的 interface 的类
>- interfaceName：指定服务提供方实现的 interface 的类名
>- version：指定服务的版本号
>- group：指定服务的分组
>- export：是否暴露服务
>- registry：是否向注册中心注册服务
>- application：应用配置
>- module：模块配置
>- provider：服务提供方配置
>- protocol：协议配置
>- monitor：监控中心配置
>- registry：注册中心配置

>***注：application、module、provider、protocol、monitor、registry（从 8 到 13）需要提供的是对应的 spring bean 
的名字，而这些 bean 的组装要么通过传统的 XML 配置方式完成，要么通过现代的 Java Config 来完成***

####@Reference

>配置 Dubbo 的服务消费方

    单个注解使用时，@Reference 可以定义在类中的一个字段上，也可以定义在一个方法上，甚至可以用来修饰另一个 annotation，表示一个服务的引用。通常 @Reference 定义在一个字段上

**@Reference注解附带属性介绍**

>- interfaceClass：指定服务的 interface 的类
>- interfaceName：指定服务的 interface 的类名
>- version：指定服务的版本号
>- group：指定服务的分组
>- url：通过指定服务提供方的 URL 地址直接绕过注册中心发起调用
>- application：应用配置
>- module：模块配置
>- consumer：服务消费方配置
>- protocol：协议配置
>- monitor：监控中心配置
>- registry：注册中心配置

***application、module、consumer、protocol、monitor、registry（从 7 到 12）需要提供的是对应的 spring bean 的名字，
而这些 bean 的组装要么通过传统的 XML 配置方式完成，要么通过现代的 Java Config 来完成***
### dubbo常见类的描述和使用

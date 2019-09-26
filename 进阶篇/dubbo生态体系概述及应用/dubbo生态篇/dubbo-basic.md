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

### dubbo schema详细说明及属性用法介绍

**&lt;dubbo:service/&gt;**

***描述：***

>服务提供者暴露服务配置。对应的配置类：org.apache.dubbo.config.ServiceConfig<br>
类似于@Service注解，但比其更丰富，用于xml配置

<details>
<summary style="color:red;">属性列表:</summary>

属性|	对应URL参数|	类型|	是否必填|	缺省值|	作用|	描述|	兼容性|
|:----:|:----:|:----:|:----:|:----:|:----:|:----:|:----|
interface|		|class|	必填	|	&nbsp;|服务发现|	服务接口名|	1.0.0以上版本|
ref|		|object|	必填	|	&nbsp;|服务发现|	服务对象实现引用|	1.0.0以上版本|
version|	|version|	string|	可选|	0.0.0	|服务发现	服务版本，建议使用两位数字版本，如：1.0，通常在接口不兼容时版本号才需要升级|	1.0.0以上版本|
group|	group|	string|	可选|		|服务发现|	服务分组，当一个接口有多个实现，可以用分组区分|	1.0.7以上版本|
path|	<path>|	string|	可选|	缺省为接口名|	服务发现|	服务路径 (注意：1.0不支持自定义路径，总是使用接口名，如果有1.0调2.0，配置服务路径可能不兼容)|	1.0.12以上版本|
delay|	delay|	int|	可选|	0	|性能调优	延迟注册服务时间(毫秒) ，设为-1时，表示延迟到Spring容器初始化完成时暴露服务|	1.0.14以上版本|
timeout|	timeout|	int|	可选|	1000	|性能调优	远程服务调用超时时间(毫秒)|	2.0.0以上版本|
retries|	retries|	int|	可选|	2	|性能调优	|远程服务调用重试次数，不包括第一次调用，不需要重试请设为0|	2.0.0以上版本|
connections|	connections|	int	|可选	|100	|性能调优	|对每个提供者的最大连接数，rmi、http、hessian等短连接协议表示限制连接数，dubbo等长连接协表示建立的长连接个数|	2.0.0以上版本
loadbalance|	loadbalance|	string|	可选	|random	|性能调优|	负载均衡策略，可选值：random,roundrobin,leastactive，分别表示：随机，轮询，最少活跃调用|	2.0.0以上版本
async|	async|	boolean|	可选|	false|	性能调优|	是否缺省异步执行，不可靠异步，只是忽略返回值，不阻塞执行线程	|2.0.0以上版本
local|	local|	class/boolean|	可选|	false|	服务治理|	设为true，表示使用缺省代理类名，即：接口名 + Local后缀，已废弃，请使用stub|	2.0.0以上版本
stub|	stub|	class/boolean|	可选|	false|	服务治理|	设为true，表示使用缺省代理类名，即：接口名 + Stub后缀，服务接口客户端本地代理类名，用于在客户端执行本地逻辑，如本地缓存等，该本地代理类的构造函数必须允许传入远程代理对象，构造函数如：public XxxServiceStub(XxxService xxxService)|	2.0.0以上版本
mock|	mock|	class/boolean|	可选|	false|	服务治理|	设为true，表示使用缺省Mock类名，即：接口名 + Mock后缀，服务接口调用失败Mock实现类，该Mock类必须有一个无参构造函数，与Local的区别在于，Local总是被执行，而Mock只在出现非业务异常(比如超时，网络异常等)时执行，Local在远程调用之前执行，Mock在远程调用后执行。|	2.0.0以上版本
token|	token|	string/boolean|	可选|	false|	服务治理|	令牌验证，为空表示不开启，如果为true，表示随机生成动态令牌，否则使用静态令牌，令牌的作用是防止消费者绕过注册中心直接访问，保证注册中心的授权功能有效，如果使用点对点调用，需关闭令牌功能|	2.0.0以上版本
registry|		|string|	可选|	缺省向所有registry注册|	配置关联|	向指定注册中心注册，在多个注册中心时使用，值为<dubbo:registry>的id属性，多个注册中心ID用逗号分隔，如果不想将该服务注册到任何registry，可将值设为N/A|	2.0.0以上版本
provider|		|string|	可选|	缺使用第一个provider配置|	配置关联|	指定provider，值为<dubbo:provider>的id属性|	2.0.0以上版本|
deprecated|	deprecated|	boolean|	可选|	false|	服务治理|	服务是否过时，如果设为true，消费方引用时将打印服务过时警告error日志|	2.0.5以上版本|
dynamic|	dynamic|	boolean|	可选|	true|	服务治理|	服务是否动态注册，如果设为false，注册后将显示后disable状态，需人工启用，并且服务提供者停止时，也不会自动取消册，需人工禁用。|	2.0.5以上版本|
accesslog|	accesslog|	string/boolean|	可选	|false	|服务治理	|设为true，将向logger中输出访问日志，也可填写访问日志文件路径，直接把访问日志输出到指定文件|	2.0.5以上版本|
owner|	owner|	string|	可选	|	|服务治理|	服务负责人，用于服务治理，请填写负责人公司邮箱前缀|	2.0.5以上版本
document|	document|	string|	可选|	&nbsp;	|服务治理	服务文档URL|	2.0.5以上版本|
weight|	weight|	int	|可选	|&nbsp;|	性能调优	|服务权重	|2.0.5以上版本|
executes|	executes|	int|	可选	|0	|性能调优	|服务提供者每服务每方法最大可并行执行请求数|	2.0.5以上版本|
proxy|	proxy|	string|	可选	|javassist|	性能调优|	生成动态代理方式，可选：jdk/javassist|	2.0.5以上版本|
cluster|	cluster|	string	|可选|	failover	|性能调优|	集群方式，可选：failover/failfast/failsafe/failback/forking|	2.0.5以上版本|
filter|	service.filter|	string|	可选|	default|	性能调优|	服务提供方远程调用过程拦截器名称，多个名称用逗号分隔|	2.0.5以上版本|
listener|	exporter.listener|	string|	可选|	default|	性能调优|	服务提供方导出服务监听器名称，多个名称用逗号分隔| &nbsp;|	
protocol|	|	string|	可选	|	|配置关联	使用指定的协议暴露服务，在多协议时使用，值为<dubbo:protocol>的id属性，多个协议ID用逗号分隔|	2.0.5以上版本|
layer|	layer|	string|	可选	|	|服务治理	服务提供者所在的分层。如：biz、dao、intl:web、china:acton。|	2.0.7以上版本|
register|	register|	boolean|	可选|	true|	服务治理	该协议的服务是否注册到注册中心	|2.0.8以上版本|

</details>

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

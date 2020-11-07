## 线程池

>线程池包含jdk内置的线程池工具类，spring的ThreadPoolTaskExecutor


###为什么使用线程池
>由于创建和销毁线程都需要很大的开销，运用线程池就可以大大的缓解这些内存开销很大的问题；可以根据系统的承受能力，调整线程池中工作线线程的数目，防止因为消耗过多的内存。

###ThreadPoolExecutor类

示例：[点我查看]()

构造方法

```java
public ThreadPoolExecutor(
        int corePoolSize,       //核心线程的数量
        int maximumPoolSize,    //最大线程数量（核心线程+核心意以外的线程）
        long keepAliveTime,     //超出核心线程数量以外的线程空闲时的存活时间
        TimeUnit unit,          //存活时间的单位
        BlockingQueue<Runnable> workQueue,    //存放来不及处理的任务的队列，是一个BlockingQueue。
        ThreadFactory threadFactory,     //生产线程的工厂类，可以定义线程名，优先级等。
        RejectedExecutionHandler handler // 当任务无法执行时的处理器
        ) {}
```

####handler处理（Reject）策略：
>1. CallerRunsPolicy：只要线程池没关闭，就直接用调用者所在线程来运行任务
>2. AbortPolicy：直接抛出 RejectedExecutionException 异常
>3. DiscardPolicy：悄悄把任务放生，不做了
>4. DiscardOldestPolicy：把队列里待最久的那个任务扔了，然后再调用 execute() 试试看能行不

**我们也可以实现自己的 RejectedExecutionHandler 接口自定义策略，比如如记录日志什么的**

####消息队列

线程池中使用的队列是 BlockingQueue 接口，常用的实现有如下几种：

>- ArrayBlockingQueue：基于数组、有界，按 FIFO（先进先出）原则对元素进行排序
>- LinkedBlockingQueue：基于链表，按FIFO （先进先出） 排序元素
>- SynchronousQueue：不存储元素的阻塞队列 ,每个插入操作必须等到另一个线程调用移除操作，否则插入操作一直处于阻塞状态
>- PriorityBlockingQueue：具有优先级的、无限阻塞队列

####JDK提供的线程池

#####newSingleThreadExecutor

>一个单线程的线程池。这个线程池只有一个线程在工作，也就是相当于单线程串行执行所有任务。如果这个唯一的线程因为异常结束，那么会有一个新的线程来替代它。此线程池保证所有任务的执行顺序按照任务的提交顺序执行。

```java
public static ExecutorService newSingleThreadExecutor() {
    return new FinalizableDelegatedExecutorService(new ThreadPoolExecutor(1, 1,
                                0L, TimeUnit.MILLISECONDS,
                                new LinkedBlockingQueue<Runnable>()));
```

#####newFixedThreadPool

>FixedThreadPool 的核心线程数和最大线程数都是指定值，也就是说当线程池中的线程数超过核心线程数后，任务都会被放到阻塞队列中。此外 keepAliveTime 为 0，也就是多余的空闲线程会被立即终止，而这里选用的阻塞队列是 LinkedBlockingQueue，使用的是默认容量 Integer.MAX_VALUE，相当于没有上限。

    执行流程：
    
    1. 线程数少于核心线程数，也就是设置的线程数时，新建线程执行任务
    2. 线程数等于核心线程数后，将任务加入阻塞队列，于队列容量非常大，可以一直加加加
    3. 执行完任务的线程反复去队列中取任务执行

```java
public static ExecutorService newFixedThreadPool(int nThreads) {
      return new ThreadPoolExecutor(nThreads, nThreads,
                                  0L, TimeUnit.MILLISECONDS,
                                  new LinkedBlockingQueue<Runnable>());
}
```
***FixedThreadPool 用于负载比较重的服务器，为了资源的合理利用，需要限制当前线程数量。***

#####newCachedThreadPool

>全部外包，没活最多待 60 秒的外包团队。
可以看到，CachedThreadPool 没有核心线程，非核心线程数无上限，也就是全部使用外包，但是每个外包空闲的时间只有 60 秒，超过后就会被回收。
CachedThreadPool 使用的队列是 SynchronousQueue，这个队列的作用就是传递任务，并不会保存。
因此当提交任务的速度大于处理任务的速度时，每次提交一个任务，就会创建一个线程。极端情况下会创建过多的线程，耗尽 CPU 和内存资源。

    执行流程：
    
    1. 没有核心线程，直接向 SynchronousQueue 中提交任务
    2. 如果有空闲线程，就去取出任务执行；如果没有空闲线程，就新建一个
    3. 执行完任务的线程有 60 秒生存时间，如果在这个时间内可以接到新任务，就可以继续活下去，否则就拜拜

```java
public static ExecutorService newCachedThreadPool() {
    return new ThreadPoolExecutor(0, Integer.MAX_VALUE,
                                  60L, TimeUnit.SECONDS,
                                  new SynchronousQueue<Runnable>());
```

***CachedThreadPool 用于并发执行大量短期的小任务，或者是负载较轻的服务器。***

#####newScheduledThreadPool

>核心和外包都有，此线程池支持定时以及周期性执行任务的需求。

```java
public class newScheduledThreadPool {
    public static void main(String[] args) {
        ScheduledExecutorService pool = Executors.newScheduledThreadPool(10);
        for (int i = 0; i < 2; i++) {
            //每隔一秒执行一次
            pool.scheduleAtFixedRate(() -> {
                System.out.println(Thread.currentThread().getName() + "\t开始发车啦....");
            }, 1, 1, TimeUnit.SECONDS);
        }
    }
}
```
####execute和submit的区别
    execute()：提交不需要返回值的任务
    submit()：提交需要返回值的任务
    
####关闭线程池
#####shutdown()
>将线程池的状态设置为 SHUTDOWN，然后中断所有没有正在执行的线程
#####shutdownNow()
>对正在执行的任务全部发出interrupt()，停止执行，对还未开始执行的任务全部取消，并且返回还没开始的任务列表。
它们的共同点是：都是通过遍历线程池中的工作线程，逐个调用 Thread.interrupt() 来中断线程，所以一些无法响应中断的任务可能永远无法停止（比如 Runnable）。

####如何合理地选择

>- CachedThreadPool 用于并发执行大量短期的小任务，或者是负载较轻的服务器
>- FixedThreadPool 用于负载比较重的服务器，为了资源的合理利用，需要限制当前线程数量
>- SingleThreadExecutor 用于串行执行任务的场景，每个任务必须按顺序执行，不需要并发执行
>- ScheduledThreadPoolExecutor 用于需要多个后台线程执行周期任务，同时需要限制线程数量的场景

***自定义线程池时，如果任务是 CPU 密集型（需要进行大量计算、处理），则应该配置尽量少的线程，
比如 CPU 个数 + 1，这样可以避免出现每个线程都需要使用很长时间但是有太多线程争抢资源的情况；
如果任务是 IO密集型（主要时间都在 I/O，CPU 空闲时间比较多），则应该配置多一些线程，比如 CPU 数的两倍，
这样可以更高地压榨 CPU。***

###ThreadPoolTaskExecutor类

>ThreadPoolTaskExecutor是一个spring的线程池技术，其实，它的实现方式完全是使用ThreadPoolExecutor进行实现。对于ThreadPoolExecutor，有一些重要的参数如下：

    （1）int corePoolSize:线程池维护线程的最小数量. 
    （2）int maximumPoolSize:线程池维护线程的最大数量. 
    （3）long keepAliveTime:空闲线程的存活时间. 
    （4）TimeUnit unit: 时间单位,现有纳秒,微秒,毫秒,秒枚举值. 
    （5）BlockingQueue<Runnable> workQueue:持有等待执行的任务队列.
    （6）RejectedExecutionHandler handler: 用来拒绝一个任务的执行。

 

####ThreadPoolExecutor池子的处理流程如下：　　

1. 当池子大小小于corePoolSize就新建线程，并处理请求

2. 当池子大小等于corePoolSize，把请求放入workQueue中，池子里的空闲线程就去从workQueue中取任务并处理

3. 当workQueue放不下新入的任务时，新建线程入池，并处理请求，如果池子大小撑到了maximumPoolSize就用RejectedExecutionHandler来做拒绝处理

4. 另外，当池子的线程数大于corePoolSize的时候，多余的线程会等待keepAliveTime长的时间，如果无请求可处理就自行销毁
其会优先创建  corePoolSize 线程， 当继续增加线程时，先放入Queue中，当 corePoolSize  和 Queue 都满的时候，就增加创建新线程，当线程达到MaxPoolSize的时候，就会抛出错 误 org.springframework.core.task.TaskRejectedException

#### 用法示例

>基于spring-boot简易方式

#####定义bean

```java
@Configuration
@EnableAsync
public class ThreadpoolConfig {

    @Bean
    public TaskExecutor taskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        // 设置核心线程数
        executor.setCorePoolSize(5);
        // 设置最大线程数
        executor.setMaxPoolSize(10);
        // 设置队列容量
        executor.setQueueCapacity(20);
        // 设置线程活跃时间（秒）
        executor.setKeepAliveSeconds(60);
        // 设置默认线程名称
        executor.setThreadNamePrefix("hello-");
        // 设置拒绝策略
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        // 等待所有任务结束后再关闭线程池
        executor.setWaitForTasksToCompleteOnShutdown(true);
        return executor;
    }
}
```
***注：***
    
    1、需要开启EnableAsync注解

使用时，添加@Async注解，如下：

ListenableFuture接口的类如AsyncResult来作为返回值的载体，用来接收任务执行结果
```java
    @Async
    public ListenableFuture<String> sayHello(String name) {
        String res = name + ":Hello World!";
        LoggerFactory.getLogger(Hello.class).info(res);
        return new AsyncResult<>(res);
    }
```

获取返回值时，调用方式：

```java
    // 阻塞调用
    hello.sayHello("yan").get();
    // 限时调用
    hello.sayHello("yan").get(1, TimeUnit.SECONDS)
```

  
 
### redis-java常见框架

>- jedis
>- lettuce
>- redisson
>- spring-data-redis的RedisTemplate,早期版本使用的是jedis作为客户端，后期使用lettuce

#### Jedis与Redisson对比

- 概况对比

>Jedis是Redis的Java实现的客户端，其API提供了比较全面的Redis命令的支持；Redisson实现了分布式和可扩展的Java数据结构，和Jedis相比，功能较为简单，不仅支持字符串操作，且还支持排序、事务、管道、分区等Redis特性。Redisson的宗旨是促进使用者对Redis的关注分离，从而让使用者能够将精力更集中地放在处理业务逻辑上。

- 编程模型

>Jedis中的方法调用是比较底层的暴露的Redis的API，也即Jedis中的Java方法基本和Redis的API保持着一致，了解Redis的API，也就能熟练的使用Jedis。而Redisson中的方法则是进行比较高的抽象，每个方法调用可能进行了一个或多个Redis方法调用。

- 可伸缩性
>- Jedis使用阻塞的I/O，且其方法调用都是同步的，程序流需要等到sockets处理完I/O才能执行，不支持异步。Jedis客户端实例不是线程安全的，所以需要通过连接池来使用Jedis。
>- Redisson使用非阻塞的I/O和基于Netty框架的事件驱动的通信层，其方法调用是异步的。Redisson的API是线程安全的，所以可以操作单个Redisson连接来完成各种操作。

- 数据结构

>- Jedis仅支持基本的数据类型如：String、Hash、List、Set、Sorted Set。

>- Redisson不仅提供了一系列的分布式Java常用对象，基本可以与Java的基本数据结构通用，还提供了许多分布式服务，其中包括（BitSet, Set, Multimap, SortedSet, Map, List, Queue, BlockingQueue, Deque, BlockingDeque, Semaphore, Lock, AtomicLong, CountDownLatch, Publish / Subscribe, Bloom filter, Remote service, Spring cache, Executor service, Live Object service, Scheduler service）。

>- 在分布式开发中，Redisson可提供更便捷的方法。


- 第三方框架整合

>1. Redisson提供了和Spring框架的各项特性类似的，以Spring XML的命名空间的方式配置RedissonClient实例和它所支持的所有对象和服务；
>2. Redisson完整的实现了Spring框架里的缓存机制；
>3. Redisson在Redis的基础上实现了Java缓存标准规范；
>4. Redisson为Apache Tomcat集群提供了基于Redis的非黏性会话管理功能。该功能支持Apache Tomcat的6、7和8版。
>5. Redisson还提供了Spring Session会话管理器的实现。

### jedis 基础使用

代码示例[](../../../code)

### lettuce 基础使用

代码示例：[lettuce](../../../code/redis-sample/lettuce-sample)

### Redisson 基础使用

代码示例[](../../../code)


### Redis持久化机制

>Redis是一个支持持久化的内存数据库，为了防止数据丢失Redis需要经常将内存中的数据同步到磁盘来保证持久化，它提供了两种持久化的方式，分别是RDB（Redis DataBase）和AOF（Append Only File）。
 

- RDB（Redis DataBase）
>简而言之，就是在不同的时间点，将redis存储的数据生成快照并存储到磁盘等介质上；
 Redis默认为该方式，内存中数据以快照的方式写入到二进制文件中，默认的文件名为dump.rdb（如果需要恢复数据，只需将备份文件dump.rdb 移动到 redis 安装目录并启动服务即可）。可以通过配置设置自动做快照持久化的方式。配置redis在n秒内如果超过m个key被修改就自动做快照，下面是默认的快照保存配置。
 
```
save 900 1         #900秒内如果超过1个key被修改，则发起快照保存
save 300 10        #300秒内容如超过10个key被修改，则发起快照保存
save 60 10000      #60秒内容如超过10000个key被修改，则发起快照保存
```
>client 也可以使用save或者bgsave命令手动通知redis做一次快照持久化。save操作是在主线程中保存快照的，由于redis是用一个主线程来处理所有 client的请求，这种方式会阻塞所有client请求，不推荐使用。
 
RDB利弊|原因
----|----
优点1|	压缩后的二进制文，适用于备份、全量复制，用于灾难恢复
优点2|	大数据量时，加载RDB恢复数据远快于AOF方式
缺点1|	无法做到实时持久化，每次都要创建子进程，频繁操作成本过高
缺点2|	保存后的二进制文件，存在老版本不兼容新版本rdb文件的问题
 

- AOF（Append Only File）

>AOF则是换了一个角度来实现持久化，那就是将redis执行过的所有写指令记录下来，在下次redis重新启动时，只要把这些写指令从前到后再重复执行一遍，就可以实现数据恢复了，相关配置如下： 

```
appendonly  no         # redis默认关闭AOF机制，可以将no改成yes实现AOF持久化
appendfsync always     # 每次有数据修改发生时都会写入AOF文件。
appendfsync everysec   # 每秒钟同步一次，该策略为AOF的缺省策略。
appendfsync no         # 从不同步。高效但是数据不会被持久化。
```

>具体操作方式：如何从AOF恢复数据？1. 设置appendonly yes；2. 将appendonly.aof放到dir参数指定的目录；3. 启动Redis，Redis会自动加载appendonly.aof文件。

>其实RDB和AOF两种方式也可以同时使用，在这种情况下，如果redis重启的话，则会优先采用AOF方式来进行数据恢复，这是因为AOF方式的数据恢复完整度更高。如果你没有数据持久化的需求，也完全可以关闭RDB和AOF方式，这样的话，redis将变成一个纯内存数据库，就像memcache一样。

`重启时恢复加载AOF与RDB顺序及流程：`
- 当AOF和RDB文件同时存在时，优先加载AOF
- 若关闭了AOF，加载RDB文件
- 加载AOF/RDB成功，redis重启成功
- AOF/RDB存在错误，redis启动失败并打印错误信息
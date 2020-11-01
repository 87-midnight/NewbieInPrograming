### redis 单机版安装

#### window安装

[redis 3.x版本下载](https://github.com/microsoftarchive/redis/releases/download/win-3.2.100/Redis-x64-3.2.100.zip)

解压后，修改redis.conf

#### centos安装

命令：
```
wget http://download.redis.io/releases/redis-5.0.7.tar.gz
tar xzf redis-5.0.7.tar.gz
cd redis-5.0.7
make
```
复制

```cmd
cp src/redis-server /usr/local/redis/redis-server
cp src/redis-cli /usr/local/redis/redis-cli
cp redis.conf /usr/local/redis/redis.conf
```

启动
```
./redis-server redis.conf
```

#### docker 安装

[redis hub页面](https://hub.docker.com/_/redis/)

下载镜像

```
docker pull redis:lastest
docker pull redis:5.0.7
```

自定义redis.conf

```dockerfile
FROM redis:lastest
COPY redis.conf /usr/local/etc/redis/redis.conf
CMD ["redis-server","/usr/local/etc/redis/redis.conf"]
```

或者在启动一个redis服务时，指定conf文件：

``docker run -itd -v /myredis/conf/redis.conf:/usr/local/etc/redis/redis.conf --name myredis redis redis-server /usr/local/etc/redis/redis.conf``


### redis主从模式安装

主机redis.conf配置信息：

```
daemonize yes
port 6379
requirepass 123456
# 一个客户端空闲多少秒后关闭连接(0代表禁用，永不关闭)
timeout 0
# 会在指定秒数和数据变化次数之后把数据库写到磁盘上
# 900秒（15分钟）之后，且至少1次变更
# 300秒（5分钟）之后，且至少10次变更
# 60秒之后，且至少10000次变更
save 900 1
save 300 10
save 60 10000
```

从机slave redis.conf配置：

```
daemonize yes
port 6379
slaveof xxx.xxx.xxx.xxx:6379
masterauth 123456
```


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



### redis集群模式安装
https://www.jianshu.com/p/c2abf726acc7

### redis 哨兵模式安装
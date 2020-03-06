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



### redis集群模式安装


### redis 哨兵模式安装
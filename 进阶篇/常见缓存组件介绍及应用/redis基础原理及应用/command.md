### redis客户端常见命令

#### 连接服务器

>redis-cli -h 127.0.0.1 -p 6379 -a "mypass"

#### key的操作

设置键值
```cmd
SET [key] [value]
```
删除key

```cmd
DEL [key]
```

查询key的剩余过期时间

```cmd
TTL [key]
```

查询key是否存在

```cmd
EXISTS [key]
```

查询key的类型

```cmd
TYPE [key]
```

获取key的value

```cmd
GET [key]
```

### docker 命令大全

#### 镜像操作

**镜像构建**
```cmd
docker build []
```

**镜像罗列**
```cmd
docker images ls
```

**镜像删除**
```cmd
docker rmi [image_id|image_name]
```

#### 查看容器

**列出所有的容器，包含正在运行，过期的，停止运行的**

```cmd
docker ps -a
```

**查找正在运行的容器**
```cmd
docker ps -a |grep Up
```

**grep结合使用**

```cmd
# 查找正在运行的、容器名包含"hello"的所有容器
docker ps -a |grep Up |grep hello
```
**查看某个容器的详细信息**

```cmd
docker inspect [container_name|container_id]
```

####  运行容器

**docker run**

>- 当使用 -P 标记时，Docker 会随机映射一个 49000~49900 的端口到内部容器开放的网络端口。
 使用 docker ps 可以看到，本地主机的 49155 被映射到了容器的 5000 端口。此时访问本机的 49155 端口即可访问容器内 web 应用提供的界面。
 
>- -p（小写）则可以指定要映射的IP和端口，但是在一个指定端口上只可以绑定一个容器。支持的格式有 hostPort:containerPort、ip:hostPort:containerPort、 ip::containerPort。
>   - hostPort:containerPort[/protocol]（映射所有接口地址）
>   - ip:hostPort:containerPort[/protocol] （映射指定地址的指定端口）
>   - ip::containerPort[/protocol] （映射指定地址的任意端口）

#### network 网络

```
docker network ls
```

创建自定义网络：
```
network create --subnet=172.18.0.0/16 mynetwork
```

启动时指定network和ip：
```
docker run -itd --name networkTest1 --net mynetwork --ip 172.18.0.2 centos:latest /bin/bash
```

#### docker compose 命令相关
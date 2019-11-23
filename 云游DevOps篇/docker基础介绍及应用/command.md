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

#### docker compose 命令相关
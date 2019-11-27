### docker基础介绍

#### docker 安装

#### Dockerfile 配置介绍

##### 常见命令

- FROM
> FROM指令初始化一个新的构建阶段，并为后续指令设置基映像。因此，有效的Dockerfile必须以FROM指令开头

**用法：**

```cmd
FROM <image> [AS <name>]
or
FROM <image>[:<tag>] [AS <name>]
or
FROM <image>[@<digest>] [AS <name>]
```

- ARG 
>变量。在FROM之前声明的参数不在生成阶段中，因此不能在FROM之后的任何指令中使用。
要使用在第一个FROM之前声明的ARG的默认值，请使用构建阶段中没有值的ARG指令：

**用法：(在from之前定义，在from定义的时候使用)**

```cmd
ARG  CODE_VERSION=latest
FROM base:${CODE_VERSION}
CMD  /code/run-app

FROM extras:${CODE_VERSION}
CMD  /code/run-extras
```

**用法：(from前后使用ARG参数)**

```cmd
ARG VERSION=latest
FROM busybox:$VERSION
ARG VERSION
RUN echo $VERSION > image_version
```

#### 网络通讯模块


部分参考：http://c.biancheng.net/view/3174.html
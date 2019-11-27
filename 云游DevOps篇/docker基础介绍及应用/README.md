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

- RUN

Run有两种方式：

-  ```RUN <command>``` 命令是在shell里面执行，在linux默认是 /bin/sh -c ，
    在Windows是 cmd /S/C
- ```RUN ["executable", "param1", "param2"](执行表单)```

>- RUN指令将在当前映像之上的新层中执行任何命令并提交结果。生成的提交映像将用于Dockerfile中的下一步。
>- 分层运行指令和生成提交符合Docker的核心概念，在Docker中，提交很便宜，可以从映像历史记录的任何一点创建容器，就像源代码管理一样。
>-exec表单可以避免shell字符串咀嚼，并使用不包含指定的shell可执行文件的基映像运行命令。
>-可以使用shell命令更改shell窗体的默认shell。

在shell表单中，您可以使用一个\（反斜杠）将一条运行指令延续到下一行。例如，考虑这两行：
```
RUN /bin/bash -c 'source $HOME/.bashrc; \
echo $HOME'
```
效果等同于：
```
RUN /bin/bash -c 'source $HOME/.bashrc; echo $HOME'
```

>Note:使用/bin/bash会比/bin/sh好很多，执行表单实则会转换为json数组，
所以必须用双引号


- CMD

CMD用法有三种形式：

- ```CMD ["executable","param1","param2"]``` 执行表单体
- ```CMD ["param1","param2"]```作为ENTRYPOINT的默认参数
- ```CMD command param1 param2``` shell表单

>一个dockerfile里只会有一个CMD，如果存在多个CMD，则以最后一个为准。
CMD最主要的目的是提供预设给一个可执行的容器，这些预设包含一个可执行命令或当用于
ENTRYPOINT时，忽略可执行命令而使用参数。


>Note:执行表单体和作为参数提供给ENTRYPOINT的这两种形式，都是用JSON格式。

>Note:与shell表单不同，exec表单不调用命令shell。这意味着正常的shell处理不会发生。
例如，```CMD[“echo”，“$HOME”]```不会对$HOME执行变量替换。如果要进行shell处理，
请使用shell表单或直接执行shell，例如：```CMD[“sh”、“-c”、“echo$HOME”]```。
当使用exec表单并直接执行shell时，与shell表单一样，执行环境变量扩展的是shell，而不是docker。


当你使用执行表单或shell表单时，在运行镜像时会把CMD作为命令执行

如果你用CMD的shell表单形式，那么将会被/bin/sh -c执行，例如：

```
FROM ubuntu
CMD echo "This is a test." | wc -
```
如果你想要在不适用/bin/sh来运行你的命令，那么则需要指定你的命令的全路径。例如：

```
FROM ubuntu
CMD ["/usr/bin/wc","--help"]
```

>Note:一个Dockerfile仅仅最后一个CMD起作用。
>- 执行文件或者没有执行文件(ENTRYPOINT提供)，为执行容器提供缺省值。
>- 如果CMD配合ENTRYPOINT那么他们的格式都需要是json数组格式，CMD用来提供参数。
>- CMD非参数模式，shell exec 当运行一个镜像的时候会执行。
>- shell格式 相当于指令在/bin/sh -c执行，如果不想使用shell格式，就需要使用数组格式，参数为单独字符串。

```
FROM ubuntu
CMD echo "This is a test." | wc -
```
```
FROM ubuntu
CMD ["/usr/bin/wc","--help"]
```
>- Note:
>- 如果容器每次都执行则考虑CMD和ENTRYPOINT结合
>- RUN镜像构建，并提交交结果。CMD构建阶段不执行，容器启动时候执行。
>- 每个Dockfile只能有一条CMD命令，如果指定了多条，只有最后一条会执行。
>- 如果用户启动容器时指定了运行命令，则会覆盖CMD指定命令。 docker run 覆盖CMD


- LABEL

```
LABEL <key>=<value> <key>=<value> <key>=<value> ...
```

>LABEL 指令会添加元数据到镜像。LABEL是以键值对形式出现的。为了在LABEL的值里面可以包含空格，你可以在命令行解析中使用引号和反斜杠。一些使用方法如下：

```
LABEL "com.example.vendor"="ACME Incorporated"
LABEL com.example.label-with-value="foo"
LABEL version="1.0"
LABEL description="This text illustrates \
that label-values can span multiple lines."
```

一个镜像可以有多个labels。你可以组合多个labels在一个LABEL里来指定多重labels。在Docker 1.10之前，这种做法可以降低最终镜像的大小，但现在不是这样。你仍然可以选择一个指令指定多个labels，使用以下的2种方法中其中一种：

```
LABEL multi.label1="value1" multi.label2="value2" other="value3"
```

```
LABEL multi.label1="value1" \
      multi.label2="value2" \
      other="value3"
```

Labels 包含在基镜像或者父母镜像（在FROM行的镜像）继承到你的镜像。如果label本身已经存在但是值不一样，最后的赋值将会覆盖前面的复制。

如果要查看镜像的labels，可以使用docker inspect命令。

```
"Labels": {
    "com.example.vendor": "ACME Incorporated"
    "com.example.label-with-value": "foo",
    "version": "1.0",
    "description": "This text illustrates that label-values can span multiple lines.",
    "multi.label1": "value1",
    "multi.label2": "value2",
    "other": "value3"
}
```

- EXPOSE

```
EXPOSE <port> [<port>/<protocol>...]
EXPOSE 80/tcp
EXPOSE 80/udp
```

>公开指令通知Docker容器在运行时监听指定的网络端口。可以指定端口是侦听TCP还是UDP，如果未指定协议，则默认为TCP。
>EXPOSE指令实际上并不发布端口。它在构建图像的人和运行容器的人之间起一种文档的作用，关于要发布的端口。要在运行容器时实际发布端口，请在docker run上使用-p标志发布和映射一个或多个端口，或使用-p标志发布所有公开的端口并将它们映射到高阶端口。
 
>docker  run 的时候指定  -P  或者 -p 将容器的端口映射到宿主机上。这样外界访问宿主机就可以获取到容器提供的服务了。
 
>-P命令可以结合这个dockerfile文件中的EXPOSE暴露的端口。会将容器中的EXPOSE端口随机映射到宿主机的端口。-P会随机分配一个宿主机的端口映射到expose端口上

>在这种情况下，如果在docker run中使用-P，则端口将为TCP和UDP公开一次。请记住，-P在主机上使用一个临时的高阶主机端口，因此TCP和UDP的端口将不同。
无论EXPOSE设置如何，都可以在运行时使用-p标志覆盖它们。-p会覆盖expose里面的端口


示例：

```
EXPOSE 80
docker run -p80:80/tcp
```

>要在主机系统上设置端口重定向，请参阅使用-P标志。docker network命令支持创建容器间通信的网络，而无需公开或发布特定端口，因为连接到网络的容器可以通过任何端口相互通信。


- ENV


#### 网络通讯模块


部分参考：http://c.biancheng.net/view/3174.html
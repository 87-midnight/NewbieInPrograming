### docker基础介绍

#### docker 安装

#### 简单安装

**centos:**

```
yum install docker
```

#### Docker CE 最新版本安装

参考: https://docs.docker.com/install/linux/docker-ce/centos/

**centos:**


Uninstall old versions
Older versions of Docker were called docker or docker-engine. If these are installed, uninstall them, along with associated dependencies.
```
sudo yum remove docker \
                  docker-client \
                  docker-client-latest \
                  docker-common \
                  docker-latest \
                  docker-latest-logrotate \
                  docker-logrotate \
                  docker-engine
```

```
sudo yum install -y yum-utils \
  device-mapper-persistent-data \
  lvm2
```
```
sudo yum-config-manager \
    --add-repo \
    https://download.docker.com/linux/centos/docker-ce.repo
```

```
sudo yum install docker-ce
```

设置为开机启动：

```
systemctl enable docker
```


#### Dockerfile 配置介绍

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
```
ARG <name>[=<default value>]
```

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

在build的时候通过docker build --build-arg var=xxx 来传递ARG参数,通常ARG可以结合ENV一起使用

**作用范围**

>一个ARG变量定义进入从在其上在限定的线效果Dockerfile不从参数对命令行或其他地方使用。例如，考虑以下Dockerfile：
```
1 FROM busybox
2 USER ${user:-some_user}
3 ARG user
4 USER $user
```
用户通过调用以下命令来构建此文件：
```
$ docker build --build-arg user=what_user .
```
USER在第2行，at some_user在user变量3上定义为。USER在第4 行，at 定义为what_useras user，并且what_user在命令行上传递了值。在通过ARG指令定义变量之前 ，任何对变量的使用都会导致一个空字符串。
的ARG指令在它被定义的构建阶段结束推移的范围进行。要在多个阶段使用arg，每个阶段都必须包含ARG指令。
```
FROM busybox
ARG SETTINGS
RUN ./run/setup $SETTINGS

FROM busybox
ARG SETTINGS
RUN ./run/other $SETTINGS
```

**使用ARG变量**

>您可以使用ARG或ENV指令来指定该RUN指令可用的变量。使用ENV指令定义的环境变量 始终会覆盖ARG同名指令。考虑此Dockerfile和ENVand ARG指令。
```
1 FROM ubuntu
2 ARG CONT_IMG_VER
3 ENV CONT_IMG_VER v1.0.0
4 RUN echo $CONT_IMG_VER

```
然后，假定此映像是使用以下命令构建的：
```
$ docker build --build-arg CONT_IMG_VER=v2.0.1 .

```
在这种情况下，该RUN指令将使用v1.0.0而不是ARG用户传递的设置：v2.0.1此行为类似于Shell脚本，其中局部作用域的变量从其定义的角度覆盖作为参数传递或从环境继承的变量。

使用上面的示例，但使用不同的ENV规范，可以在ARG和ENV指令之间创建更有用的交互：
```
1 FROM ubuntu
2 ARG CONT_IMG_VER
3 ENV CONT_IMG_VER ${CONT_IMG_VER:-v1.0.0}
4 RUN echo $CONT_IMG_VER

```
与ARG指令不同，ENV值始终保留在生成的映像中。考虑不带--build-arg标志的Docker构建：
```
$ docker build .
```

使用此Dockerfile示例，CONT_IMG_VER它仍然保留在映像中，但其值将是指令v1.0.0第3行中的默认设置ENV。

在此示例中，变量扩展技术使您可以从命令行传递参数，并利用ENV指令将其保留在最终映像中 。仅有限的一组Dockerfile指令支持变量扩展。

**预定义的ARG**

Docker具有一组预定义ARG变量，您可以在不使用ARGDockerfile中相应指令的情况下使用它们。

- HTTP_PROXY
- http_proxy
- HTTPS_PROXY
- https_proxy
- FTP_PROXY
- ftp_proxy
- NO_PROXY
- no_proxy

要使用这些，只需使用以下标志在命令行中传递它们：
```
--build-arg <varname>=<value>
```

默认情况下，这些预定义变量从的输出中排除 docker history。排除它们可以降低意外泄露HTTP_PROXY变量中的敏感身份验证信息的风险。

例如，考虑使用以下命令构建以下Dockerfile --build-arg HTTP_PROXY=http://user:pass@proxy.lon.example.com

```
FROM ubuntu
RUN echo "Hello World"
```
在这种情况下，HTTP_PROXY变量的值在中不可用， docker history也不被缓存。如果要更改位置，并且代理服务器已更改为http://user:pass@proxy.sfo.example.com，则后续的构建不会导致高速缓存未命中。

如果您需要覆盖此行为，则可以通过ARG 在Dockerfile中添加如下语句来做到这一点：
```
FROM ubuntu
ARG HTTP_PROXY
RUN echo "Hello World"
```
构建此Dockerfile时，将HTTP_PROXY保留在中 docker history，并且更改其值会使构建缓存无效。

**对构建缓存的影响**

>ARG变量不会像ENV变量那样持久化到生成的映像中。但是，ARG变量确实以类似方式影响构建缓存。如果Dockerfile定义了一个ARG其值不同于先前构建的变量，则首次使用时会发生“缓存未命中”，而不是其定义。特别是，RUN一条指令之后的所有指令都 隐式地ARG使用该ARG变量（作为环境变量），因此可能导致高速缓存未命中。ARG除非。中有匹配的ARG语句，否则所有预定义变量均免于缓存Dockerfile。

例如，考虑以下两个Dockerfile：
```
1 FROM ubuntu
2 ARG CONT_IMG_VER
3 RUN echo $CONT_IMG_VER
```
```
1 FROM ubuntu
2 ARG CONT_IMG_VER
3 RUN echo hello
```
如果--build-arg CONT_IMG_VER=<value>在命令行上指定，则在两种情况下，第2行上的指定都不会导致高速缓存未命中。第3行确实会导致缓存未命中。ARG CONT_IMG_VER导致RUN行被标识为与正在运行的CONT_IMG_VER=<value>echo hello 相同，因此，如果进行了<value> 更改，则会遇到缓存未命中的情况。

考虑同一命令行下的另一个示例：
```
1 FROM ubuntu
2 ARG CONT_IMG_VER
3 ENV CONT_IMG_VER $CONT_IMG_VER
4 RUN echo $CONT_IMG_VER
```
在此示例中，高速缓存未命中发生在第3行。之所以发生未命中，是因为变量中的变量值ENV引用了该ARG变量，并且该变量是通过命令行更改的。在此示例中，该ENV 命令使图像包含该值。

如果一条ENV指令覆盖ARG了同名的一条指令，例如Dockerfile：
```
1 FROM ubuntu
2 ARG CONT_IMG_VER
3 ENV CONT_IMG_VER hello
4 RUN echo $CONT_IMG_VER
```

第3行不会导致缓存未命中，因为的值CONT_IMG_VER是一个常量（hello）。结果，RUN（第4行）使用的环境变量和值在两次构建之间不会改变。


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

> ENV用法是把key-value的键值设置到环境变量中
这个值将出现在构建阶段中所有后续指令的环境中，并且可以在许多情况下被内联替换。

>ENV指令有两种形式。第一个表单ENV<key><value>将单个变量设置为一个值。第一个空格后的整个字符串将被视为包含空白字符的<value>。将为其他环境变量解释该值，因此如果引号字符没有转义，则将删除它们。
第二种形式ENV<key>=<value>..允许一次设置多个变量。注意，第二个表单在语法中使用等号（=），而第一个表单不使用等号（=）。与命令行解析类似，引号和反斜杠可用于在值中包含空格。

**例如：**

```
ENV myName="John Doe" myDog=Rex\ The\ Dog \
    myCat=fluffy
ENV myName John Doe
ENV myDog Rex The Dog
ENV myCat fluffy
```

ENV主要是定义环境变量，在docker run的时候ENV的配置会加载到容易内部，但ARG的参数在内部是没法看到的。同时也可以通过下面命令更改ENV的默认值：

```
docker run -e var=yyy
```

- ADD && COPY

***Build 上下文的概念***

>- 在使用 docker build 命令通过 Dockerfile 创建镜像时，会产生一个 build 上下文(context)。所谓的 build 上下文就是 docker build 命令的 PATH 或 URL 指定的路径中的文件的集合。在镜像 build 过程中可以引用上下文中的任何文件，比如我们要介绍的 COPY 和 ADD 命令，就可以引用上下文中的文件。
>- 默认情况下 docker build -t testx . 命令中的 . 表示 build 上下文为当前目录。当然我们可以指定一个目录作为上下文，比如下面的命令：

```
docker build -t testx /home/nick/hc
``` 

我们指定 /home/nick/hc 目录为 build 上下文，默认情况下 docker 会使用在上下文的根目录下找到的 Dockerfile 文件。

***COPY 和 ADD 命令不能拷贝上下文之外的本地文件***

对于 COPY 和 ADD 命令来说，如果要把本地的文件拷贝到镜像中，那么本地的文件必须是在上下文目录中的文件。其实这一点很好解释，因为在执行 build 命令时，docker 客户端会把上下文中的所有文件发送给 docker daemon。考虑 docker 客户端和 docker daemon 不在同一台机器上的情况，build 命令只能从上下文中获取文件。如果我们在 Dockerfile 的 COPY 和 ADD 命令中引用了上下文中没有的文件，就会收到类似下面的错误：
```
COPY failed:no such file or directory
```

**与 WORKDIR 协同工作**

WORKDIR 命令为后续的 RUN、CMD、COPY、ADD 等命令配置工作目录。在设置了 WORKDIR 命令后，接下来的 COPY 和 ADD 命令中的相对路径就是相对于 WORKDIR 指定的路径。比如我们在 Dockerfile 中添加下面的命令：

```
WORKDIR /app
COPY checkredis.py .
```
checkredis.py 文件就是被复制到了 WORKDIR /app 目录下。

###### COPY

如果仅仅是把本地的文件拷贝到容器镜像中，COPY 命令是最合适不过的。其命令的格式为：

```COPY <src> <dest>```

除了指定完整的文件名外，COPY 命令还支持 Go 风格的通配符，比如：

```
COPY check* /testdir/           # 拷贝所有 check 开头的文件
COPY check?.log /testdir/       # ? 是单个字符的占位符，比如匹配文件 check1.log
```
对于目录而言，COPY 和 ADD 命令具有相同的特点：只复制目录中的内容而不包含目录自身。比如我们在 Dockerfile 中添加下面的命令：
```
WORKDIR /app
COPY nickdir .
```
其中，nickdir目录下包含两个目录file1,file2.
那么/app目录下则只是包含了file1,file2.

如果想让 file1 和 file2 还保存在 nickdir 目录中，需要在目标路径中指定这个目录的名称，比如：
```
WORKDIR /app
COPY nickdir ./nickdir
```

COPY 命令区别于 ADD 命令的一个用法是在 multistage 场景下。关于 multistage 的介绍和用法请参考笔者的《Dockerfile 中的 multi-stage》一文。在 multistage 的用法中，可以使用 COPY 命令把前一阶段构建的产物拷贝到另一个镜像中，比如：

```dockerfile
FROM golang:1.7.3
WORKDIR /go/src/github.com/sparkdevo/href-counter/
RUN go get -d -v golang.org/x/net/html
COPY app.go    .
RUN CGO_ENABLED=0 GOOS=linux go build -a -installsuffix cgo -o app .

FROM alpine:latest
RUN apk --no-cache add ca-certificates
WORKDIR /root/
COPY --from=0 /go/src/github.com/sparkdevo/href-counter/app .
CMD ["./app"]
```
其中的 COPY 命令通过指定 --from=0 参数，把前一阶段构建的产物拷贝到了当前的镜像中。

###### ADD

```
ADD <src> <dest>
```

除了不能用在 multistage 的场景下，ADD 命令可以完成 COPY 命令的所有功能，并且还可以完成两类超酷的功能：

- 解压压缩文件并把它们添加到镜像中
- 从 url 拷贝文件到镜像中

**解压压缩文件并把它们添加到镜像中:**

>如果我们有一个压缩文件包，并且需要把这个压缩包中的文件添加到镜像中。需不需要先解开压缩包然后执行 COPY 命令呢？当然不需要！我们可以通过 ADD 命令一次搞定：

```
WORKDIR /app
ADD nickdir.tar.gz .
```

**从 url 拷贝文件到镜像中:**

>这是一个更加酷炫的用法！但是在 docker 官方文档的最佳实践中却强烈建议不要这么用！！docker 官方建议我们当需要从远程复制文件时，最好使用 curl 或 wget 命令来代替 ADD 命令。原因是，当使用 ADD 命令时，会创建更多的镜像层，当然镜像的 size 也会更大(下面的两段代码来自 docker 官方文档)：

```
ADD http://example.com/big.tar.xz /usr/src/things/
RUN tar -xJf /usr/src/things/big.tar.xz -C /usr/src/things
RUN make -C /usr/src/things all
```

如果使用下面的命令，不仅镜像的层数减少，而且镜像中也不包含 big.tar.xz 文件：

```
RUN mkdir -p /usr/src/things \
    && curl -SL http://example.com/big.tar.xz \
    | tar -xJC /usr/src/things \
    && make -C /usr/src/things all
```

>Note:总结，ADD命令常用于解压压缩文件并复制到镜像内


###### 加速镜像构建的技巧

>在使用 COPY 和 ADD 命令时，我们可以通过一些技巧来加速镜像的 build 过程。比如把那些最不容易发生变化的文件的拷贝操作放在较低的镜像层中，这样在重新 build 镜像时就会使用前面 build 产生的缓存

例如：checkmysql.py,checkmongo.py,checkredis.py,start.py

start.py不会经常发生变更，而其他三个文件会经常变动，那么在使用COPY命令的时候，可以按照文件/目录更新频率来分步骤使用，如：
```dockerfile
WORKDIR /root
COPY start.py .
COPY check*.py .
```
这样每次构建镜像时，只要start.py不变，就会使用cache，从而加速构建过程


- ENTRYPOINT

ENTRYPOINT有两种形式：

- ``ENTRYPOINT ["executable", "param1", "param2"]`` （执行表格，首选）
- ``ENTRYPOINT command param1 param2`` （外壳形式）

An ENTRYPOINT允许您配置将作为可执行文件运行的容器。

例如，以下将使用其默认内容启动nginx，并监听端口80：
```
docker run -i -t --rm -p 80:80 nginx
```

命令行参数to docker run <image>将以exec形式附加在所有元素之后ENTRYPOINT，并将覆盖使用指定的所有元素CMD。这允许将参数传递给入口点，即，docker run <image> -d 将-d参数传递给入口点。您可以ENTRYPOINT使用该docker run --entrypoint 标志覆盖指令。

所述壳形式防止任何CMD或run被使用命令行参数，但具有的缺点是你ENTRYPOINT将开始作为的子命令/bin/sh -c，其不通过信号。这意味着该可执行文件将不是容器的PID 1-并且不会接收Unix信号-因此您的可执行文件将不会SIGTERM从接收 到docker stop <container>。

只有其中的最后一条ENTRYPOINT指令Dockerfile才会生效。

执行表单ENTRYPOINT示例

您可以使用exec形式的ENTRYPOINT来设置相当稳定的默认命令和参数，然后使用这两种形式的CMD来设置更可能被更改的其他默认值。

```
FROM ubuntu
ENTRYPOINT ["top", "-b"]
CMD ["-c"]
```

运行容器时，可以看到这top是唯一的过程：

```cmd
docker run -it --rm --name test  top -H
top - 08:25:00 up  7:27,  0 users,  load average: 0.00, 0.01, 0.05
Threads:   1 total,   1 running,   0 sleeping,   0 stopped,   0 zombie
%Cpu(s):  0.1 us,  0.1 sy,  0.0 ni, 99.7 id,  0.0 wa,  0.0 hi,  0.0 si,  0.0 st
KiB Mem:   2056668 total,  1616832 used,   439836 free,    99352 buffers
KiB Swap:  1441840 total,        0 used,  1441840 free.  1324440 cached Mem

  PID USER      PR  NI    VIRT    RES    SHR S %CPU %MEM     TIME+ COMMAND
    1 root      20   0   19744   2336   2080 R  0.0  0.1   0:00.04 top
```
要进一步检查结果，可以使用docker exec：

```
$ docker exec -it test ps aux
USER       PID %CPU %MEM    VSZ   RSS TTY      STAT START   TIME COMMAND
root         1  2.6  0.1  19752  2352 ?        Ss+  08:24   0:00 top -b -H
root         7  0.0  0.1  15572  2164 ?        R+   08:25   0:00 ps aux
```

您也可以使用优雅地请求top关闭docker stop test。

下面Dockerfile显示了使用ENTRYPOINT来在前台运行Apache（即as PID 1）：

```
FROM debian:stable
RUN apt-get update && apt-get install -y --force-yes apache2
EXPOSE 80 443
VOLUME ["/var/www", "/var/log/apache2", "/etc/apache2"]
ENTRYPOINT ["/usr/sbin/apache2ctl", "-D", "FOREGROUND"]
```

如果需要为单个可执行文件编写启动脚本，则可以使用exec和gosu 命令确保最终的可执行文件接收Unix信号

```sbtshell
#!/usr/bin/env bash
set -e

if [ "$1" = 'postgres' ]; then
    chown -R postgres "$PGDATA"

    if [ -z "$(ls -A "$PGDATA")" ]; then
        gosu postgres initdb
    fi

    exec gosu postgres "$@"
fi

exec "$@"
```
最后，如果您需要在关闭时进行一些额外的清理（或与其他容器通信），或者协调多个可执行文件，则可能需要确保ENTRYPOINT脚本接收Unix信号，将其传递，然后执行一些更多的工作：

```
#!/bin/sh
# Note: I've written this using sh so it works in the busybox container too

# USE the trap if you need to also do manual cleanup after the service is stopped,
#     or need to start multiple services in the one container
trap "echo TRAPed signal" HUP INT QUIT TERM

# start service in background here
/usr/sbin/apachectl start

echo "[hit enter key to exit] or run 'docker stop <container>'"
read

# stop service and clean up here
echo "stopping apache"
/usr/sbin/apachectl stop

echo "exited $0"
```

如果使用来运行该映像docker run -it --rm -p 80:80 --name test apache，则可以使用docker exec或来检查容器的进程docker top，然后要求脚本停止Apache：

```
$ docker exec -it test ps aux
USER       PID %CPU %MEM    VSZ   RSS TTY      STAT START   TIME COMMAND
root         1  0.1  0.0   4448   692 ?        Ss+  00:42   0:00 /bin/sh /run.sh 123 cmd cmd2
root        19  0.0  0.2  71304  4440 ?        Ss   00:42   0:00 /usr/sbin/apache2 -k start
www-data    20  0.2  0.2 360468  6004 ?        Sl   00:42   0:00 /usr/sbin/apache2 -k start
www-data    21  0.2  0.2 360468  6000 ?        Sl   00:42   0:00 /usr/sbin/apache2 -k start
root        81  0.0  0.1  15572  2140 ?        R+   00:44   0:00 ps aux
$ docker top test
PID                 USER                COMMAND
10035               root                {run.sh} /bin/sh /run.sh 123 cmd cmd2
10054               root                /usr/sbin/apache2 -k start
10055               33                  /usr/sbin/apache2 -k start
10056               33                  /usr/sbin/apache2 -k start
$ /usr/bin/time docker stop test
test
real	0m 0.27s
user	0m 0.03s
sys	0m 0.03s
```

>- 注意：您可以使用覆盖ENTRYPOINT设置--entrypoint，但这只能将二进制文件设置为exec（sh -c将不使用）
>- 注意：exec表单被解析为JSON数组，这意味着您必须在单词而非单引号（'）周围使用双引号（“）。
>- 注意：与shell表单不同，exec表单不会调用命令shell。这意味着正常的外壳处理不会发生。例如， ENTRYPOINT [ "echo", "$HOME" ]将不会对进行变量替换$HOME。如果要进行shell处理，则可以使用shell形式或直接执行shell，例如：ENTRYPOINT [ "sh", "-c", "echo $HOME" ]。当使用exec表单并直接执行shell时（例如在shell表单中），是由shell进行环境变量扩展，而不是docker。

###### Shell形式ENTRYPOINT示例

>您可以为指定一个纯字符串ENTRYPOINT，它将在中执行/bin/sh -c。这种形式将使用外壳处理来替代外壳环境变量，并且将忽略任何CMD或docker run命令行参数。为了确保能够正确docker stop发出任何长期运行的ENTRYPOINT可执行文件信号，您需要记住以以下命令启动它exec：

```
FROM ubuntu
ENTRYPOINT exec top -b
```

运行此镜像时，您将看到单个PID 1过程：

```
$ docker run -it --rm --name test top
Mem: 1704520K used, 352148K free, 0K shrd, 0K buff, 140368121167873K cached
CPU:   5% usr   0% sys   0% nic  94% idle   0% io   0% irq   0% sirq
Load average: 0.08 0.03 0.05 2/98 6
  PID  PPID USER     STAT   VSZ %VSZ %CPU COMMAND
    1     0 root     R     3164   0%   0% top -b
```
它将在docker stop以下位置干净地退出：

```
$ /usr/bin/time docker stop test
test
real	0m 0.20s
user	0m 0.02s
sys	0m 0.04s
```

如果您忘记添加exec到您的开头ENTRYPOINT：

```
FROM ubuntu
ENTRYPOINT top -b
CMD --ignored-param1
```

然后，您可以运行它（为下一步命名）：

```
$ docker run -it --name test top --ignored-param2
Mem: 1704184K used, 352484K free, 0K shrd, 0K buff, 140621524238337K cached
CPU:   9% usr   2% sys   0% nic  88% idle   0% io   0% irq   0% sirq
Load average: 0.01 0.02 0.05 2/101 7
  PID  PPID USER     STAT   VSZ %VSZ %CPU COMMAND
    1     0 root     S     3168   0%   0% /bin/sh -c top -b cmd cmd2
    7     1 root     R     3164   0%   0% top -b
```

您可以从输出中top看到，指定ENTRYPOINT的不是PID 1。

如果随后运行docker stop test，容器将不会干净退出- 超时后将stop强制命令发送a SIGKILL：

```
$ docker exec -it test ps aux
PID   USER     COMMAND
    1 root     /bin/sh -c top -b cmd cmd2
    7 root     top -b
    8 root     ps aux
$ /usr/bin/time docker stop test
test
real	0m 10.19s
user	0m 0.04s
sys	0m 0.03s
```

###### 了解CMD和ENTRYPOINT如何相互作用

无论CMD和ENTRYPOINT指令定义运行的容器中时什么命令得到执行。很少有规则描述他们的合作。

1. Dockerfile应该至少指定CMD或ENTRYPOINT命令之一。

2. ENTRYPOINT 使用容器作为可执行文件时应定义。

3. CMD应该用作ENTRYPOINT在容器中定义命令或执行临时命令的默认参数的方式。

4. CMD 使用替代参数运行容器时，将被覆盖。

下表显示了针对不同ENTRYPOINT/ CMD组合执行的命令：

ss|没有入口点|	ENTRYPOINT exec_entry p1_entry|	ENTRYPOINT [“ exec_entry”，“ p1_entry”]|
|:----:|:----:|:----:|:----:|
没有CMD|	错误，不允许|	/ bin / sh -c exec_entry p1_entry|	exec_entry p1_entry| |
CMD [“ exec_cmd”，“ p1_cmd”]	|exec_cmd p1_cmd|	/ bin / sh -c exec_entry p1_entry|	exec_entry p1_entry exec_cmd p1_cmd||
CMD [“ p1_cmd”，“ p2_cmd”]|	p1_cmd p2_cmd|	/ bin / sh -c exec_entry p1_entry|	exec_entry p1_entry p1_cmd p2_cmd||
CMD exec_cmd p1_cmd|	/ bin / sh -c exec_cmd p1_cmd|	/ bin / sh -c exec_entry p1_entry|	exec_entry p1_entry / bin / sh -c exec_cmd p1_cmd||

>注意：如果CMD是从基础镜像定义的，则设置ENTRYPOINT将重置CMD为空值。在这种情况下，CMD必须在当前镜像中定义一个值。


- VOLUME

```
VOLUME ["/data"]
```
>该VOLUME指令创建具有指定名称的安装点，并将其标记为保存来自本地主机或其他容器的外部安装的卷。该值可以是JSON数组，也可以是VOLUME ["/var/log/"]具有多个参数的纯字符串，例如VOLUME /var/log或VOLUME /var/log /var/db。有关通过Docker客户端的更多信息/示例和安装说明，请参阅 通过Volumes共享目录 。

docker run命令使用基础映像内指定位置上存在的任何数据初始化新创建的卷。例如，考虑以下Dockerfile片段：
```
FROM ubuntu
RUN mkdir /myvol
RUN echo "hello world" > /myvol/greeting
VOLUME /myvol
```

该Dockerfile生成一个映像，该映像导致docker run在处创建一个新的挂载点/myvol并将该greeting文件复制 到新创建的卷中。

请注意以下几点Dockerfile

- 基于Windows的容器上的卷：使用基于Windows的容器时，容器内的卷的目的地必须是以下之一：
    - 不存在或空目录
    - 除以下驱动器 C:
- 从Dockerfile中更改卷：如果在声明了卷之后有任何构建步骤更改了卷中的数据，则这些更改将被丢弃。
- JSON格式：列表被解析为JSON数组。您必须用双引号（"）而不是单引号（'）括住单词。
- 主机目录是在容器运行时声明的：主机目录（挂载点）从本质上说是依赖于主机的。这是为了保留图像的可移植性，因为不能保证给定的主机目录在所有主机上都可用。因此，您无法从Dockerfile内挂载主机目录。该VOLUME指令不支持指定host-dir 参数。创建或运行容器时，必须指定安装点。

- WORKDIR

```
WORKDIR /path/to/workdir
```

该WORKDIR指令集的工作目录对任何RUN，CMD， ENTRYPOINT，COPY和ADD它后面的说明Dockerfile。如果WORKDIR不存在，那么即使以后的任何Dockerfile指令中都没有使用它，也将创建它。

该WORKDIR指令可以在中多次使用Dockerfile。如果提供了相对路径，则它将相对于上一条WORKDIR指令的路径 。例如：
```
WORKDIR /a
WORKDIR b
WORKDIR c
RUN pwd
```
最终的输出pwd命令这Dockerfile将是 /a/b/c。

该WORKDIR指令可以解析先前使用设置的环境变量 ENV。您只能使用在中显式设置的环境变量Dockerfile。例如：
```
ENV DIRPATH /path
WORKDIR $DIRPATH/$DIRNAME
RUN pwd
```

最终pwd命令的输出Dockerfile是 /path/$DIRNAME

- ONBUILD

```
ONBUILD [INSTRUCTION]
```

该ONBUILD指令将映像用作另一个构建的基础时，将在以后的时间向该映像添加触发指令。触发器将在下游构建的上下文中执行，就像它已FROM在下游指令之后立即插入一样 Dockerfile。

任何构建指令都可以注册为触发器。

如果要构建的图像将用作构建其他图像的基础，例如应用程序构建环境或可以使用用户特定配置自定义的守护程序，则此功能很有用。

例如，如果您的映像是可重用的Python应用程序构建器，则将需要在特定目录中添加应用程序源代码，并且此后可能需要调用构建脚本 。你不能只是打电话ADD和RUN现在，因为你还没有访问应用程序的源代码，这将是为每个应用程序生成不同的。您可以简单地为应用程序开发人员提供Dockerfile可复制粘贴到其应用程序中的样板，但这效率低下，容易出错且难以更新，因为它与特定于应用程序的代码混合在一起。

解决方案是使用ONBUILD注册预先的指令，以便在下一个构建阶段中稍后运行。

运作方式如下：

- 当遇到ONBUILD指令时，构建器将触发器添加到正在构建的图像的元数据中。该指令不会影响当前版本。
- 在构建结束时，所有触发器的列表都存储在映像清单的key下OnBuild。可以使用docker inspect命令检查它们。
- 稍后，可以使用该FROM指令将该图像用作新版本的基础 。作为处理FROM指令的一部分，下游构建器将查找ONBUILD触发器，并以注册时的顺序执行它们。如果任何触发器失败，则该FROM指令将中止，从而导致构建失败。如果所有触发器都成功，则FROM指令将完成，并且构建将照常继续。
- 执行完触发器后，将从最终图像中清除触发器。换句话说，它们不是“孙子代”版本所继承的。

例如，您可以添加以下内容：
```
[...]
ONBUILD ADD . /app/src
ONBUILD RUN /usr/local/bin/python-build --dir /app/src
[...]
```
>- 警告：不允许ONBUILD使用链接说明ONBUILD ONBUILD。
>- 警告：该ONBUILD指令可能不会触发FROM或执行MAINTAINER。

- STOPSIGNAL 

>该STOPSIGNAL指令设置将被发送到容器退出的系统调用信号。该信号可以是与内核syscall表中的位置匹配的有效无符号数字（例如9），也可以是格式为SIGNAME的信号名称（例如SIGKILL）。


- HEALTHCHECK 

该HEALTHCHECK指令有两种形式：

- HEALTHCHECK [OPTIONS] CMD command （通过在容器内部运行命令来检查容器的运行状况）
- HEALTHCHECK NONE （禁用从基本映像继承的任何运行状况检查）

该HEALTHCHECK指令告诉Docker如何测试容器以检查其是否仍在工作。这样可以检测到诸如Web服务器陷入无限循环并且无法处理新连接的情况，即使服务器进程仍在运行。

当指定了运行状况检查的容器时，除了其正常状态外，它还具有运行状况。此状态最初为starting。只要运行状况检查通过，它将变为healthy（以前处于任何状态）。在一定数量的连续失败之后，它变为unhealthy。

之前可能出现的选项CMD是：

- --interval=DURATION（默认值：30s）
- --timeout=DURATION（默认值：30s）
- --start-period=DURATION（默认值：0s）
- --retries=N（默认值：3）

运行状况检查将首先在容器启动后的间隔秒数内运行，然后在之前每次检查完成后的间隔秒数内运行。

如果单次检查花费的时间超过超时秒数，则认为检查失败。

对于要考虑的容器，需要重试连续进行的运行状况检查失败unhealthy。

**开始时间段**为需要时间进行引导的容器提供了初始化时间。在此期间内的探针故障将不计入最大重试次数。但是，如果运行状况检查在启动期间成功，则认为该容器已启动，并且所有连续失败将计入最大重试次数。

HEALTHCHECKDockerfile中只能有一条指令。如果您列出多个，则只有最后一个HEALTHCHECK才会生效。

CMD关键字后面的命令可以是shell命令（例如HEALTHCHECK CMD /bin/check-running）或exec数组（与其他Dockerfile命令一样；ENTRYPOINT有关详细信息，请参见例如）。

该命令的退出状态指示容器的健康状态。可能的值为：

- 0：成功-容器健康且可以使用
- 1：不健康-容器无法正常工作
- 2：保留-请勿使用此退出代码

例如，要每五分钟检查一次，以便网络服务器能够在三秒钟内为站点的主页提供服务：
```
HEALTHCHECK --interval=5m --timeout=3s \
CMD curl -f http://localhost/ || exit 1
```

为了帮助调试失败的探针，命令在stdout或stderr上写入的任何输出文本（UTF-8编码）都将存储在运行状况中，并可以通过查询 docker inspect。此类输出应保持简短（当前仅存储前4096个字节）。

容器的健康状态发生更改时，将health_status生成具有新状态的事件。

该HEALTHCHECK功能已在Docker 1.12中添加。

- SHELL

```
SHELL ["executable", "parameters"]
```
该SHELL指令允许覆盖用于命令的shell形式的默认shell 。在Linux上["/bin/sh", "-c"]，默认的shell是，在Windows 上，默认的shell 是["cmd", "/S", "/C"]。该SHELL指令必须以JSON格式编写在Dockerfile中。

该SHELL指令在Windows上特别有用，在Windows上有两个常用且完全不同的本机shell：cmd和powershell，以及可用的替代shell包括sh。

该SHELL说明可以出现多次。每个SHELL指令将覆盖所有先前的SHELL指令，并影响所有后续的指令。例如：

```
FROM microsoft/windowsservercore

# Executed as cmd /S /C echo default
RUN echo default

# Executed as cmd /S /C powershell -command Write-Host default
RUN powershell -command Write-Host default

# Executed as powershell -command Write-Host hello
SHELL ["powershell", "-command"]
RUN Write-Host hello

# Executed as cmd /S /C echo hello
SHELL ["cmd", "/S", "/C"]
RUN echo hello
```
以下说明可以通过影响SHELL指令时， 壳他们的形式在一个Dockerfile使用：RUN，CMD和ENTRYPOINT。

以下示例是Windows上常见的模式，可通过使用SHELL指令进行精简：
```
...
RUN powershell -command Execute-MyCmdlet -param1 "c:\foo.txt"
...
```
docker调用的命令将是：
```
cmd /S /C powershell -command Execute-MyCmdlet -param1 "c:\foo.txt"
```

这效率低下有两个原因。首先，有一个不必要的cmd.exe命令处理器（又名Shell）被调用。其次，shell 形式的每条RUN指令都需要在命令前加上前缀。powershell -command

为了使其更有效，可以采用两种机制之一。一种是使用RUN命令的JSON形式，例如：
```
...
RUN ["powershell", "-command", "Execute-MyCmdlet", "-param1 \"c:\\foo.txt\""]
...
```
尽管JSON形式是明确的，并且不使用不必要的cmd.exe，但它确实需要通过双引号和转义来实现更多的详细信息。另一种机制是使用SHELL指令和外壳程序形式，使Windows用户的语法更自然，尤其是与escapeparser指令结合使用时：
```
# escape=`

FROM microsoft/nanoserver
SHELL ["powershell","-command"]
RUN New-Item -ItemType Directory C:\Example
ADD Execute-MyCmdlet.ps1 c:\example\
RUN c:\example\Execute-MyCmdlet -sample 'hello world'
```

导致：
```
PS E:\docker\build\shell> docker build -t shell .
Sending build context to Docker daemon 4.096 kB
Step 1/5 : FROM microsoft/nanoserver
 ---> 22738ff49c6d
Step 2/5 : SHELL powershell -command
 ---> Running in 6fcdb6855ae2
 ---> 6331462d4300
Removing intermediate container 6fcdb6855ae2
Step 3/5 : RUN New-Item -ItemType Directory C:\Example
 ---> Running in d0eef8386e97


    Directory: C:\


Mode                LastWriteTime         Length Name
----                -------------         ------ ----
d-----       10/28/2016  11:26 AM                Example


 ---> 3f2fbf1395d9
Removing intermediate container d0eef8386e97
Step 4/5 : ADD Execute-MyCmdlet.ps1 c:\example\
 ---> a955b2621c31
Removing intermediate container b825593d39fc
Step 5/5 : RUN c:\example\Execute-MyCmdlet 'hello world'
 ---> Running in be6d8e63fe75
hello world
 ---> 8e559e9bf424
Removing intermediate container be6d8e63fe75
Successfully built 8e559e9bf424
PS E:\docker\build\shell>
```

该SHELL指令还可用于修改外壳的操作方式。例如，SHELL cmd /S /C /V:ON|OFF在Windows上使用，可以修改延迟的环境变量扩展语义。

如果SHELL需要备用shell，例如，和其他zsh，该指令也可以在Linux上使用。cshtcsh

该SHELL功能已在Docker 1.12中添加。


#### 网络通讯模块


部分参考：http://c.biancheng.net/view/3174.html
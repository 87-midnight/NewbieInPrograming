### netty 粘包拆包处理方案

https://zhuanlan.zhihu.com/p/87370086


#### netty 对象转换字节流，自定义拆包粘包

https://www.cnblogs.com/fanguangdexiaoyuer/p/6131042.html


### linux系统环境 + udp epoll高并发

添加额外的依赖包

```xml
<dependency>
  <groupId>io.netty</groupId>
  <artifactId>netty-transport-native-epoll</artifactId>
  <version>4.1.39.Final</version>
</dependency>
```

- udp 服务端

核心代码
```java
public void start()throws Exception{
        boss = new EpollEventLoopGroup(5);
        Bootstrap server = new Bootstrap();
        server.group(boss)
                .channel(EpollDatagramChannel.class)
                .option(ChannelOption.SO_RCVBUF, 1024*64)
                .option(ChannelOption.SO_SNDBUF, 1024*64)
                .option(ChannelOption.SO_REUSEADDR,true)
                .handler(new ChannelInitializer<EpollDatagramChannel>() {
                    @Override
                    protected void initChannel(EpollDatagramChannel ch) throws Exception {
                        ch.pipeline().addLast(new UdpEpollHandler());
                    }
                });
        Channel channel = server.bind(new InetSocketAddress(port)).sync().channel();
        if (channel.isActive()){
            parent = channel;
            log.info("udp epoll服务端启动成功");
        }
    }
    public void sendMessage(){
        String msg = "你好，客户端。";
        clients_epoll.forEach((k,v)->{
            ByteBuf byteBuf = Unpooled.wrappedBuffer(msg.getBytes());
            DatagramPacket packet = new DatagramPacket(byteBuf, v);
            parent.writeAndFlush(packet);
        });
    }
```

- udp epoll 客户端

```java
 public void start() throws InterruptedException {
        group = new EpollEventLoopGroup(5);
        Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(group)
                .channel(EpollDatagramChannel.class)
                .option(ChannelOption.SO_RCVBUF, 1024*64)
                .option(ChannelOption.SO_SNDBUF, 1024*64)
                .handler(new ChannelInitializer<EpollDatagramChannel>(){

                    @Override
                    protected void initChannel(EpollDatagramChannel ch) throws Exception {
                        ch.pipeline().addLast(new UdpEpollClientHandler());
                    }
                });
        this.parent = bootstrap.connect(new InetSocketAddress("localhost",this.port)).sync().channel();
    }

    public void sendMessage(){
        String msg = "你好，服务端。";
        ByteBuf byteBuf = Unpooled.wrappedBuffer(msg.getBytes());
        DatagramPacket packet = new DatagramPacket(byteBuf, (InetSocketAddress) parent.remoteAddress());
        parent.writeAndFlush(packet);

    }


```


打包jar。

使用命令启动main函数：
```cmd
java -cp xxx.jar com.xxx.xxx.MainClass
```

### udp 丢包分析及处理方案

https://www.jianshu.com/p/22b0f89937ef
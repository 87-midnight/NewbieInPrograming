### netty tcp基础篇

#### tcp发送二进制数据

- 服务端

```java
@Slf4j
public class TcpBasicServer {

    private Integer port;

    private Channel server;

    private EventLoopGroup boss;
    private EventLoopGroup group;

    public void startServer()throws Exception{
        ServerBootstrap sb = new ServerBootstrap();
        boss = new NioEventLoopGroup(5);
        group = new NioEventLoopGroup(5);
        sb.group(boss,group)
                .channel(NioServerSocketChannel.class)
                .option(ChannelOption.SO_REUSEADDR,true)
                .option(ChannelOption.SO_KEEPALIVE,true)
                .option(ChannelOption.SO_RCVBUF,1024*64)
                .option(ChannelOption.SO_SNDBUF,1024*64)
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                        ch.pipeline().addLast(new ByteArrayEncoder());
                        ch.pipeline().addLast(new ByteArrayDecoder());
                        ch.pipeline().addLast(new BasicMessageHandler());
                    }
                });
        ChannelFuture f = sb.bind(new InetSocketAddress(port)).await();
        if (f.isSuccess()){
            server = f.channel();
            log.info("Tcp服务器启动成功");
        }
    }
    
    public void close(){
        server.close();
        boss.shutdownGracefully();
        group.shutdownGracefully();
    }
}

```

`服务端handler`

```java
public class BasicMessageHandler extends SimpleChannelInboundHandler<byte[]> {
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, byte[] bytes) throws Exception {
        log.info("收到客户端["+channelHandlerContext.channel().id()+"]发给我的消息：【"+new String(bytes)+"】");
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        clients.putIfAbsent(ctx.channel().id().asLongText(),ctx.channel());
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        clients.remove(ctx.channel().id().asLongText());
    }

    @Override
    public boolean isSharable() {
        return true;
    }
}
```

- 客户端

```java
 public void startClient()throws Exception{
        EventLoopGroup group = new NioEventLoopGroup();
        Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(group).channel(NioSocketChannel.class);
        bootstrap.handler(new ChannelInitializer<Channel>() {
            @Override
            protected void initChannel(Channel ch) throws Exception{
                ch.pipeline().addLast(new ByteArrayEncoder());
                ch.pipeline().addLast(new ByteArrayDecoder());
                ch.pipeline().addLast(new BasicStringHandler());
            }
        });
        bootstrap.option(ChannelOption.SO_KEEPALIVE,true);
        ChannelFuture f = bootstrap.connect(new InetSocketAddress(host,serverPort)).sync();
        if (f.isSuccess()){
            client = f.channel();
            log.info("连接TCP服务器"+host+"成功");
            client.writeAndFlush("hello".getBytes());
        }
    }

```

`客户端handler`

```java
public class BasicStringHandler extends SimpleChannelInboundHandler<byte[]> {
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, byte[] bytes) throws Exception {
        log.info("收到服务器发给我的消息："+new String(bytes));
    }

    @Override
    public boolean isSharable() {
        return true;
    }
}
```


### netty udp 基础篇

- udp 服务端

核心代码

```java
public void start() throws InterruptedException {
        group = new NioEventLoopGroup();
        Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(group)
                .channel(NioDatagramChannel.class)
                .option(ChannelOption.SO_RCVBUF, 1024*64)
                .option(ChannelOption.SO_SNDBUF, 1024*64)
                .handler(new ChannelInitializer<NioDatagramChannel>(){

                    @Override
                    protected void initChannel(NioDatagramChannel ch) throws Exception {
                        ch.pipeline().addLast(new UdpBasicServerHandler());
                    }
                });
        this.parent = bootstrap.bind(new InetSocketAddress(this.port)).sync().channel();
        if (this.parent.isActive()){
            log.info("udp服务端启动成功");
        }
    }
```

广播消息，处理客户端消息

```java
public static final Map<String,InetSocketAddress> clients_udp = new ConcurrentHashMap<>();

public void sendMessage(){
        String msg = "你好，客户端。";
        clients_udp.forEach((k,v)->{
            ByteBuf byteBuf = Unpooled.wrappedBuffer(msg.getBytes());
            DatagramPacket packet = new DatagramPacket(byteBuf, v);
            parent.writeAndFlush(packet);
        });
    }
```

```java

@ChannelHandler.Sharable
@Slf4j
public class UdpBasicServerHandler extends SimpleChannelInboundHandler<DatagramPacket> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, DatagramPacket msg) throws Exception {
        ByteBuf buf = msg.content();
        int len = buf.readableBytes();
        byte[] data = new byte[len];
        buf.readBytes(data);
        String receive = new String(data,"UTF-8");
        clients_udp.put(msg.sender().toString(),  msg.sender());
        log.info("收到客户端的消息：【{}】",receive);
        log.info("clients:{}",clients_udp);
    }

}

```

- udp 客户端

```java
public void start() throws InterruptedException {
        group = new NioEventLoopGroup();
        Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(group)
                .channel(NioDatagramChannel.class)
                .option(ChannelOption.SO_RCVBUF, 1024*64)
                .option(ChannelOption.SO_SNDBUF, 1024*64)
                .handler(new ChannelInitializer<NioDatagramChannel>(){

                    @Override
                    protected void initChannel(NioDatagramChannel ch) throws Exception {
                        ch.pipeline().addLast(new UdpBasicClientHandler());
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

处理消息
```java
@ChannelHandler.Sharable
@Slf4j
public class UdpBasicClientHandler extends SimpleChannelInboundHandler<DatagramPacket> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, DatagramPacket msg) throws Exception {
        ByteBuf buf = msg.content();
        int len = buf.readableBytes();
        byte[] data = new byte[len];
        buf.readBytes(data);
        String receive = new String(data,"UTF-8");
        log.info("收到服务端的消息：【{}】",receive);
    }
}
```



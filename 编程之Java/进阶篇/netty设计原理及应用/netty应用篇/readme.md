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

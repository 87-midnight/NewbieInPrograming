### Channel介绍

通常来说NIO中的所有IO都是从 Channel（通道） 开始的。

从通道进行数据读取 ：创建一个缓冲区，然后请求通道读取数据。

从通道进行数据写入 ：创建一个缓冲区，填充数据，并要求通道写入数据。

![](static/channel.png)

Java NIO Channel通道和流非常相似，主要有以下几点区别：

- 通道可以读也可以写，流一般来说是单向的（只能读或者写，所以之前我们用流进行IO操作的时候需要分别创建一个输入流和一个输出流）。

- 通道可以异步读写。

- 通道总是基于缓冲区Buffer来读写。


Java NIO中最重要的几个Channel的实现：

- FileChannel： 用于文件的数据读写

- DatagramChannel： 用于UDP的数据读写

- SocketChannel： 用于TCP的数据读写，一般是客户端实现

- ServerSocketChannel: 允许我们监听TCP链接请求，每个请求会创建会一个SocketChannel，一般是服务器实现


### FileChannel的使用

使用FileChannel读取数据到Buffer（缓冲区）以及利用Buffer（缓冲区）写入数据到FileChannel：


```java

```


通过上述实例代码，我们可以大概总结出FileChannel的一般使用规则：

    1. 开启FileChannel

使用之前，FileChannel必须被打开 ，但是你无法直接打开FileChannel（FileChannel是抽象类）。需要通过 InputStream ， OutputStream 或 RandomAccessFile 获取FileChannel。

我们上面的例子是通过RandomAccessFile打开FileChannel的：

```java

//1.创建一个RandomAccessFile（随机访问文件）对象
RandomAccessFile raf=new RandomAccessFile("D:\\niodata.txt","rw");
//通过RandomAccessFile对象的getChannel()方法。FileChannel是抽象类。      
FileChannel inChannel=raf.getChannel();
```
    2. 从FileChannel读取数据/写入数据

从FileChannel中读取数据/写入数据之前首先要创建一个Buffer（缓冲区）对象，Buffer（缓冲区）对象的使用我们在上一篇文章中已经详细说明了，如果不了解的话可以看我的上一篇关于Buffer的文章。

使用FileChannel的read()方法读取数据：

使用FileChannel的write()方法写入数据：

    
    3. 关闭FileChannel


### SocketChannel和ServerSocketChannel的使用

利用SocketChannel和ServerSocketChannel实现客户端与服务器端简单通信：

SocketChannel 用于创建基于tcp协议的客户端对象，因为SocketChannel中不存在accept()方法，所以，它不能成为一个服务端程序。通过 connect()方法 ，SocketChannel对象可以连接到其他tcp服务器程序。

客户端:

ServerSocketChannel 允许我们监听TCP链接请求，通过ServerSocketChannelImpl的 accept()方法 可以创建一个SocketChannel对象用户从客户端读/写数据。

服务端：


客户端
1.通过SocketChannel连接到远程服务器
2.创建读数据/写数据缓冲区对象来读取服务端数据或向服务端发送数据
3.关闭SocketChannel
服务端
1.通过ServerSocketChannel 绑定ip地址和端口号
2.通过ServerSocketChannelImpl的accept()方法创建一个SocketChannel对象用户从客户端读/写数据
3.创建读数据/写数据缓冲区对象来读取客户端数据或向客户端发送数据
4. 关闭SocketChannel和ServerSocketChannel


### DatagramChannel的使用

DataGramChannel，类似于java 网络编程的DatagramSocket类；使用UDP进行网络传输， UDP是无连接，面向数据报文段的协议，对传输的数据不保证安全与完整 ；和上面介绍的SocketChannel和ServerSocketChannel的使用方法类似，所以这里就简单介绍一下如何使用。

1.获取DataGramChannel

2.接收/发送消息

接收消息：

先创建一个缓存区对象，然后通过receive方法接收消息，这个方法返回一个SocketAddress对象，表示发送消息方的地址：

发送消息：

由于UDP下，服务端和客户端通信并不需要建立连接，只需要知道对方地址即可发出消息，但是是否发送成功或者成功被接收到是没有保证的;发送消息通过send方法发出，改方法返回一个int值，表示成功发送的字节数：


### Scatter / Gather

Channel 提供了一种被称为 Scatter/Gather 的新功能，也称为本地矢量 I/O。Scatter/Gather 是指在多个缓冲区上实现一个简单的 I/O 操作。正确使用 Scatter / Gather可以明显提高性能。

大多数现代操作系统都支持本地矢量I/O（native vectored I/O）操作。当您在一个通道上请求一个Scatter/Gather操作时，该请求会被翻译为适当的本地调用来直接填充或抽取缓冲区，减少或避免了缓冲区拷贝和系统调用；

Scatter/Gather应该使用直接的ByteBuffers以从本地I/O获取最大性能优势。

Scatter/Gather功能是通道(Channel)提供的 并不是Buffer。

Scatter: 从一个Channel读取的信息分散到N个缓冲区中(Buufer).

Gather: 将N个Buffer里面内容按照顺序发送到一个Channel.

Scattering Reads
"scattering read"是把数据从单个Channel写入到多个buffer,如下图所示：
![](static/scatter-read.png)

read()方法内部会负责把数据按顺序写进传入的buffer数组内。一个buffer写满后，接着写到下一个buffer中。

举个例子，假如通道中有200个字节数据，那么header会被写入128个字节数据，body会被写入72个字节数据；

注意：

无论是scatter还是gather操作，都是按照buffer在数组中的顺序来依次读取或写入的；

Gathering Writes
"gathering write"把多个buffer的数据写入到同一个channel中，下面是示意图：
![](static/gather-write.png)


write()方法内部会负责把数据按顺序写入到channel中。

注意：

并不是所有数据都写入到通道，写入的数据要根据position和limit的值来判断，只有position和limit之间的数据才会被写入；

举个例子，假如以上header缓冲区中有128个字节数据，但此时position=0，limit=58；那么只有下标索引为0-57的数据才会被写入到通道中。

### 通道之间的数据传输

在Java NIO中如果一个channel是FileChannel类型的，那么他可以直接把数据传输到另一个channel。

transferFrom() :transferFrom方法把数据从通道源传输到FileChannel

transferTo() :transferTo方法把FileChannel数据传输到另一个channel








> PS: https://mp.weixin.qq.com/s?__biz=Mzg2OTA0Njk0OA==&mid=2247484949&amp;idx=1&amp;sn=a8a9c3fcf736efa88917e8c32db35758&source=41#wechat_redirect
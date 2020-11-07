package com.lcg.nio.selector;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * @author linchuangang
 * @createTime 2020/11/7
 **/
public class SocketChannelServer {

    public static void main(String[] args) throws Exception{

        ServerSocketChannel server = ServerSocketChannel.open();
        server.bind(new InetSocketAddress(5520));
        server.configureBlocking(false);

        Selector selector = Selector.open();
        server.register(selector, SelectionKey.OP_ACCEPT);

        ByteBuffer readBuff  = ByteBuffer.allocate(1024);
        ByteBuffer writeBuff  = ByteBuffer.allocate(1024);
        writeBuff.put("received".getBytes());
        writeBuff.flip();

        while (true) {
            int nReady = selector.select();
            Set<SelectionKey> keys = selector.selectedKeys();
            Iterator<SelectionKey> it = keys.iterator();

            while (it.hasNext()) {
                SelectionKey key = it.next();
                it.remove();

                if (key.isAcceptable()) {
                    // 创建新的连接，并且把连接注册到selector上，而且，
                    // 声明这个channel只对读操作感兴趣。
                    SocketChannel socketChannel = server.accept();
                    socketChannel.configureBlocking(false);
                    socketChannel.register(selector, SelectionKey.OP_READ);
                }
                else if (key.isReadable()) {
                    SocketChannel socketChannel = (SocketChannel) key.channel();
                    readBuff.clear();
                    socketChannel.read(readBuff);

                    readBuff.flip();
                    System.out.println("received : " + new String(readBuff.array()));
                    key.interestOps(SelectionKey.OP_WRITE);
                }
                else if (key.isWritable()) {
                    writeBuff.rewind();
                    SocketChannel socketChannel = (SocketChannel) key.channel();
                    socketChannel.write(writeBuff);
                    key.interestOps(SelectionKey.OP_READ);
                }
            }
        }
    }
}

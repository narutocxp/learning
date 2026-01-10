package com.shopping.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

public class Server {
    public static void main(String[] args) throws IOException {
        Selector selector = Selector.open();
        ServerSocketChannel ssc = ServerSocketChannel.open();
        ssc.bind(new InetSocketAddress(8080));
        ssc.configureBlocking(false);
        SelectionKey sscKey = ssc.register(selector, 0, null);
        System.out.println("sscKey:" + sscKey);
        sscKey.interestOps(SelectionKey.OP_ACCEPT);

        while (true) {
            System.out.println("select before");
            selector.select();
            System.out.println("select after");
            Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
            while (iterator.hasNext()) {
                SelectionKey selectionKey = iterator.next();
                System.out.println("select:" + selectionKey);
                ServerSocketChannel channel = (ServerSocketChannel) selectionKey.channel();
                SocketChannel socketChannel = channel.accept();
                socketChannel.configureBlocking(false);
                System.out.println("socketChannel:" + socketChannel);
            }

        }
    }
}

package com.shopping.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;

public class Client {
    public static void main(String[] args) throws IOException {
        SocketChannel socketChannel = SocketChannel.open();
        socketChannel.connect(new InetSocketAddress("127.0.0.1", 8080));
        socketChannel.write(Charset.defaultCharset().encode("hello"));
        socketChannel.write(Charset.defaultCharset().encode("123456"));
        System.in.read();
        System.out.println("connecting ...");
    }
}

package com.shopping.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.util.Iterator;
import java.util.concurrent.ConcurrentLinkedQueue;

public class BossServer {
    public static void main(String[] args) throws IOException {
        Thread.currentThread().setName("boss");
        Selector selector = Selector.open();
        ServerSocketChannel ssc = ServerSocketChannel.open();
        ssc.bind(new InetSocketAddress(8080));
        ssc.configureBlocking(false);
        ssc.register(selector, SelectionKey.OP_ACCEPT, null);
        Worker worker = new Worker("worker-0");
        // 需要自旋
        while (true) {
            selector.select();
            Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
            while (iterator.hasNext()) {
                SelectionKey selectionKey = iterator.next();
                iterator.remove();
                if (selectionKey.isAcceptable()) {
                    ServerSocketChannel channel = (ServerSocketChannel) selectionKey.channel();
                    SocketChannel socketChannel = channel.accept();
                    worker.register(socketChannel);
                }
            }
        }
    }

    private static class Worker implements Runnable {
        private Selector selector;
        private final String name;
        private volatile boolean start;
        private ConcurrentLinkedQueue<Runnable> queue = new ConcurrentLinkedQueue<>();

        public Worker(String name) {
            this.name = name;
        }

        public void register(SocketChannel channel) {
            if (!start) {
                try {
                    selector = Selector.open();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                new Thread(this, name).start();
                start = true;
            }
            queue.add(() -> {
                try {
                    channel.configureBlocking(false);
                    channel.register(selector, SelectionKey.OP_READ, null);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });
            selector.wakeup();  // 唤醒select，才能触发selector.select()
        }

        @Override
        public void run() {
            // 需要自旋
            while (true) {
                try {
                    selector.select();
                    Runnable task = queue.poll();
                    if (task != null) {
                        task.run();
                    }
                    Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
                    while (iterator.hasNext()) {
                        SelectionKey selectionKey = iterator.next();
                        iterator.remove();
                        if (selectionKey.isReadable()) {
                            SocketChannel channel = (SocketChannel) selectionKey.channel();
                            ByteBuffer byteBuffer = ByteBuffer.allocate(16);
                            channel.read(byteBuffer);
                            // 需要设置读模式
                            byteBuffer.flip();
                            System.out.println(Charset.defaultCharset().decode(byteBuffer));
                        }
                    }
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
}

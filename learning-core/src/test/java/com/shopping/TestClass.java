package com.shopping;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringEncoder;

import java.net.InetSocketAddress;

public class TestClass {
    public static void main(String[] args) throws InterruptedException {
        ChannelFuture connect = new Bootstrap().group(new NioEventLoopGroup()).channel(NioSocketChannel.class).handler(new ChannelInitializer<NioSocketChannel>() {
            @Override
            protected void initChannel(NioSocketChannel nioSocketChannel) throws Exception {
                nioSocketChannel.pipeline().addLast(new StringEncoder());
            }
        }).connect(new InetSocketAddress("127.0.0.1", 8080));
        ChannelFuture sync = connect.sync();
        Channel channel = sync.channel();
        channel.writeAndFlush("hello");
    }
}

package com.shopping.netty.two;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.DefaultEventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.nio.charset.Charset;

public class Server {
    public static void main(String[] args) {
        DefaultEventLoopGroup normal = new DefaultEventLoopGroup();
        new ServerBootstrap().group(new NioEventLoopGroup(), new NioEventLoopGroup()).channel(NioServerSocketChannel.class).childHandler(new ChannelInitializer<NioSocketChannel>() {
            @Override
            protected void initChannel(NioSocketChannel nioSocketChannel) throws Exception {
                nioSocketChannel.pipeline().addLast(new ChannelInboundHandlerAdapter() {
                    @Override
                    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
                        ByteBuf byteBuf = (ByteBuf) msg;
                        System.out.println(Thread.currentThread().getName() + "-" + byteBuf.toString(Charset.defaultCharset()));
                        ctx.fireChannelRead(msg);
                    }
                });
                nioSocketChannel.pipeline().addLast(normal, "handler2", new ChannelInboundHandlerAdapter() {
                    @Override
                    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
                        ByteBuf byteBuf = (ByteBuf) msg;
                        System.out.println(Thread.currentThread().getName() + "-" + byteBuf.toString(Charset.defaultCharset()));
                    }
                });
            }
        }).bind(8080);
    }
}

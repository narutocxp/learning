package com.shopping.netty.three;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.AdaptiveRecvByteBufAllocator;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.FixedLengthFrameDecoder;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LineBasedFrameDecoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import lombok.extern.slf4j.Slf4j;

/**
 * 粘包，半包问题
 */
@Slf4j
public class Server {
    /**
     * 短连接测试服务器（短连接只能解决粘包不能处理半包问题）
     */
    public void testServer1() {
        NioEventLoopGroup boss = new NioEventLoopGroup();
        NioEventLoopGroup worker = new NioEventLoopGroup();
        try {
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            // 调整系统的接受缓冲器(滑动窗口)
            // serverBootstrap.option(ChannelOption.SO_RCVBUF, 10);
            // 调整netty的接收缓冲区(byteBuf)
            serverBootstrap.childOption(ChannelOption.RCVBUF_ALLOCATOR, new AdaptiveRecvByteBufAllocator(16, 16, 16));
            ChannelFuture channelFuture = serverBootstrap.group(boss, worker).channel(NioServerSocketChannel.class).childHandler(new ChannelInitializer<NioSocketChannel>() {
                @Override
                protected void initChannel(NioSocketChannel ch) throws Exception {
                    ch.pipeline().addLast(new LoggingHandler(LogLevel.DEBUG));
                }

            }).bind(8080);
            channelFuture.channel().closeFuture().sync();
        } catch (Exception e) {
            log.error("error", e);
        } finally {
            boss.shutdownGracefully();
            worker.shutdownGracefully();
        }
    }

    /**
     * 定长解码器测试服务器
     */

    public void testServer2() {
        NioEventLoopGroup boss = new NioEventLoopGroup();
        NioEventLoopGroup worker = new NioEventLoopGroup();
        try {
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            // 调整系统的接受缓冲器(滑动窗口)
            // serverBootstrap.option(ChannelOption.SO_RCVBUF, 10);
            // 调整netty的接收缓冲区(byteBuf)
            serverBootstrap.childOption(ChannelOption.RCVBUF_ALLOCATOR, new AdaptiveRecvByteBufAllocator(16, 16, 16));
            ChannelFuture channelFuture = serverBootstrap.group(boss, worker).channel(NioServerSocketChannel.class).childHandler(new ChannelInitializer<NioSocketChannel>() {
                @Override
                protected void initChannel(NioSocketChannel ch) throws Exception {
                    // 找到所有消息最长的作为定长,等到10个解一个，不够就等，但是要统一10个字节一起发送就浪费了。
                    ch.pipeline().addLast(new FixedLengthFrameDecoder(10));
                    ch.pipeline().addLast(new LoggingHandler(LogLevel.DEBUG));
                }

            }).bind(8080);
            channelFuture.channel().closeFuture().sync();
        } catch (Exception e) {
            log.error("error", e);
        } finally {
            boss.shutdownGracefully();
            worker.shutdownGracefully();
        }
    }


    /**
     * 换行解码器测试服务器
     * LineBasedFrameDecoder 换行解码器 需要定义一个长度，如果在这个长度没找到换行，则会抛出 TooLongFrameException
     * DelimiterBasedFrameDecoder 换行解码器 需要定义一个分隔符，如果分隔符没找到，则会抛出 TooLongFrameException
     */

    public void testServer3() {
        NioEventLoopGroup boss = new NioEventLoopGroup();
        NioEventLoopGroup worker = new NioEventLoopGroup();
        try {
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            // 调整系统的接受缓冲器(滑动窗口)
            // serverBootstrap.option(ChannelOption.SO_RCVBUF, 10);
            // 调整netty的接收缓冲区(byteBuf)
            serverBootstrap.childOption(ChannelOption.RCVBUF_ALLOCATOR, new AdaptiveRecvByteBufAllocator(16, 16, 16));
            ChannelFuture channelFuture = serverBootstrap.group(boss, worker).channel(NioServerSocketChannel.class).childHandler(new ChannelInitializer<NioSocketChannel>() {
                @Override
                protected void initChannel(NioSocketChannel ch) throws Exception {
                    // 超过这个最大长度1024没找到换行符就失败
                    ch.pipeline().addLast(new LineBasedFrameDecoder(1024));
                    ch.pipeline().addLast(new LoggingHandler(LogLevel.DEBUG));
                }

            }).bind(8080);
            channelFuture.channel().closeFuture().sync();
        } catch (Exception e) {
            log.error("error", e);
        } finally {
            boss.shutdownGracefully();
            worker.shutdownGracefully();
        }
    }

    /**
     * 长度字段解码器测试服务器
     * LengthFieldBasedFrameDecoder 长度字段解码器
     * maxFrameLength 帧的最大长度，超过最大长度没发现分割标准就失败了
     * lengthFieldOffset 长度字段的偏移量
     * lengthFieldLength 长度字段的长度
     * lengthAdjustment 长度字段的调整,以长度字段为基准，还有几个字节是内容
     * initialBytesToStrip 初始的字节跳过，从头剥离几个字节
     */

    public void testServer4() {
        NioEventLoopGroup boss = new NioEventLoopGroup();
        NioEventLoopGroup worker = new NioEventLoopGroup();
        try {
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            // 调整系统的接受缓冲器(滑动窗口)
            // serverBootstrap.option(ChannelOption.SO_RCVBUF, 10);
            // 调整netty的接收缓冲区(byteBuf)
            serverBootstrap.childOption(ChannelOption.RCVBUF_ALLOCATOR, new AdaptiveRecvByteBufAllocator(16, 16, 16));
            ChannelFuture channelFuture = serverBootstrap.group(boss, worker).channel(NioServerSocketChannel.class).childHandler(new ChannelInitializer<NioSocketChannel>() {
                @Override
                protected void initChannel(NioSocketChannel ch) throws Exception {
                    // 超过这个最大长度1024没找到换行符就失败
                    ch.pipeline().addLast(new LengthFieldBasedFrameDecoder(1024, 0, 2));
                    ch.pipeline().addLast(new LoggingHandler(LogLevel.DEBUG));
                }

            }).bind(8080);
            channelFuture.channel().closeFuture().sync();
        } catch (Exception e) {
            log.error("error", e);
        } finally {
            boss.shutdownGracefully();
            worker.shutdownGracefully();
        }
    }
}

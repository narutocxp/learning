package com.shopping.netty.three;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.channel.*;
import io.netty.channel.embedded.EmbeddedChannel;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.net.InetSocketAddress;
import java.util.Random;

/**
 * 粘包，半包问题
 */
@Slf4j
public class Client {
    /**
     * 短连接测试客户端 （短连接只能解决粘包不能处理半包问题）
     */
    @Test
    public void testClient1() {
        for (int i = 0; i < 10; i++) {
            send1();
        }
        log.info("finish");
    }

    /**
     * 定长解码器测试
     */
    @Test
    public void testClient2() {
        send2();
        log.info("finish");
    }

    /**
     * 换行解码器测试
     */
    @Test
    public void testClient3() {
        send3();
        log.info("finish");
    }


    /**
     * 长度字段解码器
     */
    @Test
    public void testClient4() {
        EmbeddedChannel channel = new EmbeddedChannel(new LengthFieldBasedFrameDecoder(1024, 0, 4, 0, 0), new LoggingHandler(LogLevel.DEBUG));

        ByteBuf buffer = ByteBufAllocator.DEFAULT.buffer();
        writeContent(buffer, "hello,world");
        writeContent(buffer, "test");
        channel.writeInbound(buffer);
    }

    private static void writeContent(ByteBuf buffer, String content) {
        byte[] bytes = content.getBytes();
        buffer.writeInt(bytes.length);
        buffer.writeBytes(bytes);
    }

    private static void send1() {
        NioEventLoopGroup work = new NioEventLoopGroup();
        try {
            ChannelFuture channelFuture = new Bootstrap().group(work).channel(NioSocketChannel.class).handler(new ChannelInitializer<NioSocketChannel>() {
                @Override
                protected void initChannel(NioSocketChannel ch) throws Exception {
                    ch.pipeline().addLast(new ChannelInboundHandlerAdapter() {
                        @Override
                        public void channelActive(ChannelHandlerContext ctx) throws Exception {
                            ByteBuf buffer = ctx.alloc().buffer(16);
                            buffer.writeBytes(new byte[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15});
                            ctx.writeAndFlush(buffer);
                            ctx.channel().close();
                        }
                    });
                }
            }).connect(new InetSocketAddress("127.0.0.1", 8080));
            Channel channel = channelFuture.sync().channel();
            channel.closeFuture().sync();
        } catch (InterruptedException e) {
            log.error("error", e);
        } finally {
            work.shutdownGracefully();
        }
    }

    /**
     * 定长解码器
     */
    private void send2() {
        NioEventLoopGroup work = new NioEventLoopGroup();
        try {
            ChannelFuture channelFuture = new Bootstrap().group(work).channel(NioSocketChannel.class).handler(new ChannelInitializer<NioSocketChannel>() {
                @Override
                protected void initChannel(NioSocketChannel ch) throws Exception {
                    ch.pipeline().addLast(new LoggingHandler(LogLevel.DEBUG));
                    ch.pipeline().addLast(new ChannelInboundHandlerAdapter() {
                        @Override
                        public void channelActive(ChannelHandlerContext ctx) throws Exception {
                            ByteBuf byteBuf = ctx.alloc().buffer();
                            char c = '0';
                            Random random = new Random();
                            for (int i = 0; i < 10; i++) {
                                byte[] bytes = fillBytes(c, random.nextInt(10) + 1, 10);
                                c++;
                                byteBuf.writeBytes(bytes);
                            }
                            ctx.writeAndFlush(byteBuf);
                        }
                    });
                }
            }).connect(new InetSocketAddress("127.0.0.1", 8080));
            Channel channel = channelFuture.sync().channel();
            channel.closeFuture().sync();
        } catch (InterruptedException e) {
            log.error("error", e);
        } finally {
            work.shutdownGracefully();
        }
    }


    private void send3() {
        NioEventLoopGroup work = new NioEventLoopGroup();
        try {
            ChannelFuture channelFuture = new Bootstrap().group(work).channel(NioSocketChannel.class).handler(new ChannelInitializer<NioSocketChannel>() {
                @Override
                protected void initChannel(NioSocketChannel ch) throws Exception {
                    ch.pipeline().addLast(new LoggingHandler(LogLevel.DEBUG));
                    ch.pipeline().addLast(new ChannelInboundHandlerAdapter() {
                        @Override
                        public void channelActive(ChannelHandlerContext ctx) throws Exception {
                            ByteBuf buffer = ctx.alloc().buffer();
                            char c = '0';
                            Random random = new Random();
                            for (int i = 0; i < 10; i++) {
                                StringBuilder stringBuilder = makeString(c, random.nextInt(256) + 1);
                                c++;
                                buffer.writeBytes(stringBuilder.toString().getBytes());
                            }
                            ctx.writeAndFlush(buffer);
                        }
                    });
                }
            }).connect(new InetSocketAddress("127.0.0.1", 8080));
            Channel channel = channelFuture.sync().channel();
            channel.closeFuture().sync();
        } catch (InterruptedException e) {
            log.error("error", e);
        } finally {
            work.shutdownGracefully();
        }
    }

    public static byte[] fillBytes(char c, int len, int maxLen) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < len; i++) {
            sb.append(c);
        }
        for (int i = 0; i < maxLen - len; i++) {
            sb.append("_");
        }
        System.out.println(sb.toString());
        return sb.toString().getBytes();
    }

    public static StringBuilder makeString(char c, int len) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < len; i++) {
            sb.append(c);
        }
        sb.append("\n");
        return sb;
    }

    public static void main(String[] args) {
        System.out.println(fillBytes('a', 3, 10));
    }
}

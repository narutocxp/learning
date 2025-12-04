package com.shopping.netty.three;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.net.InetSocketAddress;

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
            send();
        }
        log.info("finish");
    }

    private static void send() {
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
}

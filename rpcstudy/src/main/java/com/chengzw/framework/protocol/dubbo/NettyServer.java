package com.chengzw.framework.protocol.dubbo;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;

/**
 * Netty服务端
 * @author 程治玮
 * @since 2021/3/31 11:12 下午
 */
public class NettyServer {

    public void start(String hostName, int port) {
        try {
            final ServerBootstrap bootstrap = new ServerBootstrap();
            NioEventLoopGroup eventLoopGroup = new NioEventLoopGroup();
            bootstrap.group(eventLoopGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<SocketChannel>() {

                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            ChannelPipeline pipeline = socketChannel.pipeline();
                            pipeline.addLast("decoder", new ObjectDecoder(ClassResolvers
                                    .weakCachingConcurrentResolver(this.getClass()
                                            .getClassLoader())));
                            pipeline.addLast("encoder", new ObjectEncoder());
                            pipeline.addLast("handler", new NettyServerHandler());
                        }
                    });
            bootstrap.bind(hostName, port).sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void close() {

    }
}

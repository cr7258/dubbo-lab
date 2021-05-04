package com.chengzw.framework.protocol.dubbo;

import com.chengzw.framework.Invocation;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


/**
 * Netty客户端
 * @author 程治玮
 * @since 2021/3/31 11:09 下午
 */
public class NettyClient {

    public NettyClientHandler client = null;

    private static ExecutorService executorService = Executors.newCachedThreadPool();

    public void start(String hostName, Integer port) {
        client = new NettyClientHandler();

        Bootstrap b = new Bootstrap();
        EventLoopGroup group = new NioEventLoopGroup();
        b.group(group)
                .channel(NioSocketChannel.class)
                .option(ChannelOption.TCP_NODELAY, true)
                .handler(new ChannelInitializer<SocketChannel>() {
                    protected void initChannel(SocketChannel socketChannel) throws Exception {
                        ChannelPipeline pipeline = socketChannel.pipeline();
                        pipeline.addLast("decoder", new ObjectDecoder(ClassResolvers
                                .weakCachingConcurrentResolver(this.getClass()
                                        .getClassLoader())));
                        pipeline.addLast("encoder", new ObjectEncoder());
                        pipeline.addLast("handler", client);
                    }
                });

        try {
            b.connect(hostName, port).sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public String send(String hostName, Integer port, Invocation invocation) {
        if (client == null) {
            start(hostName, port);
        }

        client.setInvocation(invocation);
        try {
            return (String) executorService.submit(client).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return null;
    }


}
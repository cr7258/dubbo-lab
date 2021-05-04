package com.chengzw.framework.protocol.dubbo;

import com.chengzw.framework.Invocation;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import java.util.concurrent.Callable;


/**
 * @author 程治玮
 * @since 2021/3/31 11:10 下午
 */
public class NettyClientHandler extends ChannelInboundHandlerAdapter implements Callable {

    private ChannelHandlerContext context;
    private Invocation invocation;
    private String result;

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        context = ctx;
    }

    @Override
    public synchronized void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        result = msg.toString();
        notify();
    }

    public synchronized Object call() throws Exception {
        context.writeAndFlush(this.invocation);
        wait();
        return result;
    }

    public void setInvocation(Invocation invocation) {
        this.invocation = invocation;
    }
}
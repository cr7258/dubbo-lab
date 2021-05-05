package com.chengzw.framework.protocol.dubbo;

import com.chengzw.framework.Invocation;
import com.chengzw.framework.register.FileMapRegister;
import com.chengzw.framework.register.ZookeeperRegister;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.lang.reflect.Method;

/**
 * 服务提供者处理请求的逻辑
 * @author 程治玮
 * @since 2021/3/31 11:10 下午
 */

public class NettyServerHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        Invocation invocation = (Invocation) msg;

        //方式一：从本地文化根据接口名获取服务具体实现类
        // Class implClass = FileMapRegister.getImplClass(invocation.getInterfaceName());

        //方式二：从Zookeeper注册中心获取服务具体实现类
        Class implClass = ZookeeperRegister.getImplClass(invocation.getInterfaceName());
        //获取方法
        Method method = implClass.getMethod(invocation.getMethodName(), invocation.getParamTypes());
        //执行方法
        Object result = method.invoke(implClass.newInstance(), invocation.getParams());
        System.out.println("Netty服务器收到: " + result.toString());
        //返回结果
        ctx.writeAndFlush("Netty:" + result);
    }
}

package com.chengzw.framework.protocol.dubbo;

import com.chengzw.framework.Invocation;
import com.chengzw.framework.Protocol;
import com.chengzw.framework.URL;

/**
 * 实现Protocol接口
 * @author 程治玮
 * @since 2021/3/31 11:19 下午
 */
public class DubboProtocol implements Protocol {
    @Override
    public void start(URL url) {
        NettyServer nettyServer = new NettyServer();
        nettyServer.start(url.getHostname(),url.getPort());
    }

    @Override
    public String send(URL url, Invocation invocation) {
        return new NettyClient().send(url.getHostname(),url.getPort(),invocation);
    }
}

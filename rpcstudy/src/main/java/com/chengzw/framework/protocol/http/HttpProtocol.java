package com.chengzw.framework.protocol.http;

import com.chengzw.framework.Invocation;
import com.chengzw.framework.Protocol;
import com.chengzw.framework.URL;

/**
 * 实现Protocol接口
 * @author 程治玮
 * @since 2021/3/31 11:18 下午
 */
public class HttpProtocol implements Protocol {
    @Override
    public void start(URL url) {
        HttpServer httpServer = new HttpServer();
        httpServer.start(url.getHostname(),url.getPort());
    }

    @Override
    public String send(URL url, Invocation invocation) {
        return new HttpClient().send(url.getHostname(),url.getPort(),invocation);
    }
}

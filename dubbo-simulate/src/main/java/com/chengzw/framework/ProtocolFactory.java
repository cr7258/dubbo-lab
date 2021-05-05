package com.chengzw.framework;

import com.chengzw.framework.protocol.dubbo.DubboProtocol;
import com.chengzw.framework.protocol.http.HttpProtocol;
import org.springframework.beans.factory.annotation.Value;

/**
 * 读取配置文件决定服务端和客户端使用http还是dubbo协议
 * 工厂模式，解决协议切换的问题
 * @author 程治玮
 * @since 2021/3/31 11:23 下午
 */

public class ProtocolFactory {

    @Value("${protocol}")
    private static String name;

    public static Protocol getProtocol() {
        if (name == null || name.equals("")) name = "http";
        switch (name) {
            case "http":
                return new HttpProtocol();
            case "dubbo":
                return new DubboProtocol();
            default:
                break;
        }
        return new HttpProtocol();

    }
}

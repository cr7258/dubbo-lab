package com.chengzw.framework;

/**
 * HTTP和Dubbo协议都实现该接口
 * 策略模式
 * @author 程治玮
 * @since 2021/3/31 11:17 下午
 */
public interface Protocol {

    void start(URL url);
    String send(URL url,Invocation invocation);
}

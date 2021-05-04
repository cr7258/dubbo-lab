package com.chengzw.consumer;

import com.chengzw.framework.ProxyFactory;
import com.chengzw.provider.api.HelloService;

/**
 * 服务消费者
 * @author 程治玮
 * @since 2021/3/30 11:35 下午
 */
public class Consumer {
    public static void main(String[] args) {

        //得到代理对象
        HelloService helloService = ProxyFactory.getProxy(HelloService.class);
        //当调用sayHello()方法时，实际上是去调用代理对象的invoke()方法
        String result = helloService.sayHello("chengzw好帅");
        System.out.println(result);

    }
}

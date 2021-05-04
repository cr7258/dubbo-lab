package com.tuling.consumer;

import com.tuling.DemoService;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.ConfigurableApplicationContext;

import java.io.IOException;

/**
 * 本地存根示例消费端
 */
@EnableAutoConfiguration
public class StubDubboConsumerDemo {

    //写法一：
    //@Reference(version = "timeout", timeout = 1000, stub = "com.tuling.DemoServiceStub")

    //写法二：会用 demoService 的类全名 com.tuling.DemoService 拼接上 Stub，然后去找这个类
    //只要这个类在消费端的classpath中能找到就行
    @Reference(version = "timeout", timeout = 1000, stub = "true")
    private DemoService demoService;

    public static void main(String[] args) throws IOException {
        ConfigurableApplicationContext context = SpringApplication.run(StubDubboConsumerDemo.class);

        DemoService demoService = context.getBean(DemoService.class);

        System.out.println((demoService.sayHello("chengzw")));


    }

}

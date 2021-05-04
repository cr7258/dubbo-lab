package com.tuling.consumer;

import com.tuling.DemoService;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.ConfigurableApplicationContext;

import java.io.IOException;

/**
 * 参数回调示例消费端
 */
@EnableAutoConfiguration
public class CallbackDubboConsumerDemo {


    @Reference(version = "callback")
    private DemoService demoService;

    public static void main(String[] args) throws IOException {
        ConfigurableApplicationContext context = SpringApplication.run(CallbackDubboConsumerDemo.class);

        DemoService demoService = context.getBean(DemoService.class);

        // 用来进行callback
        System.out.println(demoService.sayHello("chengzw", "d1", new DemoServiceListenerImpl()));
        System.out.println(demoService.sayHello("chengzw", "d2", new DemoServiceListenerImpl()));
        System.out.println(demoService.sayHello("chengzw", "d3", new DemoServiceListenerImpl()));
    }

}

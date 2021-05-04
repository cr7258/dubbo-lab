package com.tuling.consumer;

import com.tuling.DemoService;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.ConfigurableApplicationContext;

import java.io.IOException;

/**
 * 服务降级示例消费端
 */
@EnableAutoConfiguration
public class MockDubboConsumerDemo {

    //如果消费者调用服务端失败，不抛出异常，而是返回 123
    @Reference(version = "timeout", timeout = 1000, mock = "fail: return 123")
    private DemoService demoService;

    public static void main(String[] args) throws IOException {
        ConfigurableApplicationContext context = SpringApplication.run(MockDubboConsumerDemo.class);

        DemoService demoService = context.getBean(DemoService.class);

        System.out.println((demoService.sayHello("chengzw")));
    }

}

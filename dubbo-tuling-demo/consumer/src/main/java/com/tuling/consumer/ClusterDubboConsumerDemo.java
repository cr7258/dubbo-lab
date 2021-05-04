package com.tuling.consumer;

import com.tuling.DemoService;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.ConfigurableApplicationContext;

import java.io.IOException;

/**
 * 集群容错示例消费端
 */
@EnableAutoConfiguration
public class ClusterDubboConsumerDemo {


    @Reference(version = "timeout", timeout = 1000, cluster = "failfast")
    private DemoService demoService;

    public static void main(String[] args) throws IOException {
        ConfigurableApplicationContext context = SpringApplication.run(ClusterDubboConsumerDemo.class);

        DemoService demoService = context.getBean(DemoService.class);

        System.out.println((demoService.sayHello("chengzw")));

    }

}

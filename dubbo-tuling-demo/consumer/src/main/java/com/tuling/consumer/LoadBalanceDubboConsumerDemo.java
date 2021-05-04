package com.tuling.consumer;

import com.tuling.DemoService;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.ConfigurableApplicationContext;

import java.io.IOException;

/**
 * 负载均衡示例消费端
 */
@EnableAutoConfiguration
public class LoadBalanceDubboConsumerDemo {


    // 轮询算法测试
    @Reference(version = "default", loadbalance = "roundrobin")
    // 一致性hash算法测试
    // @Reference(version = "default", loadbalance = "consistenthash")
    private DemoService demoService;

    public static void main(String[] args) throws IOException {
        ConfigurableApplicationContext context = SpringApplication.run(LoadBalanceDubboConsumerDemo.class);

        DemoService demoService = context.getBean(DemoService.class);

        // 轮询算法测试
        for (int i = 0; i < 1000; i++) {
            System.out.println((demoService.sayHello("chengzw")));
            try {
                Thread.sleep(1 * 1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        // 一致性hash算法测试
//        for (int i = 0; i < 1000; i++) {
//            System.out.println((demoService.sayHello(i%5+"chengzw")));
//            try {
//                Thread.sleep(1 * 1000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        }
    }

}

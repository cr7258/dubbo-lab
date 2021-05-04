package com.chengzw;

import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author 程治玮
 * @since 2021/4/2 11:56 下午
 */
public class Consumer {
    public static void main(String[] args) throws Exception {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(new String[] {"consumer.xml"});
        context.start();
        DemoService demoService = (DemoService)context.getBean("demoService"); // 获取远程服务代理
        String hello = demoService.sayHello("chengzw"); // 执行远程方法
        System.out.println( hello ); // 显示调用结果
    }
}
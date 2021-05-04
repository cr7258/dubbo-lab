package com.chengzw;
import org.springframework.context.support.ClassPathXmlApplicationContext;
/**
 * @author 程治玮
 * @since 2021/4/2 11:45 下午
 */

public class Provider {
    public static void main(String[] args) throws Exception {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(new String[]{"provider.xml"});
        context.start();
        System.in.read(); // 按任意键退出
    }
}
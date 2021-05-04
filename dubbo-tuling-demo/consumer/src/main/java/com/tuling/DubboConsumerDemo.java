package com.tuling;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.tuling.controller.ConsumerInterceptor;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.io.IOException;

@SpringBootApplication
public class DubboConsumerDemo implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new ConsumerInterceptor());
    }

    //指定引入服务的版本（dubbo才会产生demoService的代理对象），在其他地方才可以@Autowired注入
    @Reference(version = "default")
    private DemoService demoService;

    public static void main(String[] args) throws IOException {
        ConfigurableApplicationContext context = SpringApplication.run(DubboConsumerDemo.class);

        //dubbo生成的代理对象
        DemoService demoService = context.getBean(DemoService.class);
        System.out.println((demoService.sayHello("chengzw")));
    }

}

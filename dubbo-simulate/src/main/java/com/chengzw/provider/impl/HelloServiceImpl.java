package com.chengzw.provider.impl;

import com.chengzw.provider.api.HelloService;

/**
 * 服务实现类
 * @author 程治玮
 * @since 2021/3/30 10:11 下午
 */
public class HelloServiceImpl implements HelloService {
    @Override
    public String sayHello(String name) {
        return "hello " + name;
    }
}


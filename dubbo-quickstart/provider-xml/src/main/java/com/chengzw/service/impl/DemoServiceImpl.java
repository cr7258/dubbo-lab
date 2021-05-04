package com.chengzw.service.impl;

import com.chengzw.DemoService;

/**
 * @author 程治玮
 * @since 2021/4/2 11:39 下午
 */
public class DemoServiceImpl implements DemoService {
    public String sayHello(String name) {
        return "Hello " + name;
    }
}
package com.chengzw.provider;

import com.chengzw.framework.Protocol;
import com.chengzw.framework.ProtocolFactory;
import com.chengzw.framework.URL;
import com.chengzw.framework.register.ZookeeperRegister;
import com.chengzw.provider.api.HelloService;
import com.chengzw.provider.impl.HelloServiceImpl;

/**
 * 服务提供者
 * @author 程治玮
 * @since 2021/3/30 10:12 下午
 */
public class Provider {
    public static void main(String[] args) {


        //方式一：将服务提供者信息写入文件，供消费者读取
        //FileMapRegister.regist(HelloService.class.getName(),HelloServiceImpl.class.getName(),"localhost:8081");

        //方式二：将服务提供者信息注册到Zookeeper
        ZookeeperRegister.regist(HelloService.class.getName(),HelloServiceImpl.class.getName(),"localhost:8081");

        Protocol protocol = ProtocolFactory.getProtocol();
        protocol.start(new URL("localhost",8081));
    }
}

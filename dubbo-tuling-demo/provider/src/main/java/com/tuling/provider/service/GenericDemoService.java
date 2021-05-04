package com.tuling.provider.service;

import com.tuling.DemoService;
import org.apache.dubbo.config.annotation.Service;
import org.apache.dubbo.rpc.service.GenericException;
import org.apache.dubbo.rpc.service.GenericService;

import javax.ws.rs.Path;

@Service(interfaceName = "com.tuling.DemoService", version = "generic", protocol = {"p1", "p2", "p3"})
public class GenericDemoService implements GenericService {

    @Override
    public Object $invoke(String s, String[] strings, Object[] objects) throws GenericException {
        System.out.println("执行了generic服务");

        return "执行的方法是" + s;
    }
}

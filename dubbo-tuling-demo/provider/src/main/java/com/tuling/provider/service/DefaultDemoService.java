package com.tuling.provider.service;

import com.tuling.DemoService;
import org.apache.dubbo.common.URL;
import org.apache.dubbo.config.annotation.Service;
import org.apache.dubbo.rpc.RpcContext;

import javax.ws.rs.Path;


@Service(version = "default", protocol = {"p1", "p2", "p3"})
public class DefaultDemoService implements DemoService {

    @Path("say")
    @Override
    public String sayHello(String name) {
        System.out.println("执行了服务" + name);

        URL url = RpcContext.getContext().getUrl();
        return String.format("%s：%s, Hello, %s", url.getProtocol(), url.getPort(), name);  // 正常访问
    }


}

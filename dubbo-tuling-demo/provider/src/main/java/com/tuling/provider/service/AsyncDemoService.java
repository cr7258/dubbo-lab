package com.tuling.provider.service;

import com.tuling.DemoService;
import org.apache.dubbo.common.URL;
import org.apache.dubbo.config.annotation.Service;
import org.apache.dubbo.rpc.RpcContext;

import javax.ws.rs.Path;
import java.util.concurrent.CompletableFuture;

/**
 * 异步调用示例服务端
 */
@Service(version = "async", protocol = {"p1", "p2", "p3"})
public class AsyncDemoService implements DemoService {

    public String sayHello(String name) {
        System.out.println("执行了同步服务" + name);
        URL url = RpcContext.getContext().getUrl();
        return String.format("%s：%s, Hello, %s", url.getProtocol(), url.getPort(), name);  // 正常访问
    }

    @Override
    public CompletableFuture<String> sayHelloAsync(String name) {
        System.out.println("执行了异步服务" + name);

        return CompletableFuture.supplyAsync(() -> {
            return sayHello(name);
        });
    }
}

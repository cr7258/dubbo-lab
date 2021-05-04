package com.tuling.provider.service;

import com.tuling.DemoService;
import org.apache.dubbo.common.URL;
import org.apache.dubbo.config.annotation.Service;
import org.apache.dubbo.rpc.RpcContext;

import javax.ws.rs.Path;
import java.util.concurrent.TimeUnit;


/**
 * 超时示例服务端
 */

@Service(version = "timeout", timeout = 6000, protocol = {"p1", "p2", "p3"})
public class TimeoutDemoService implements DemoService {

    @Override
    public String sayHello(String name) {
        System.out.println("执行了timeout服务" + name);

        // 服务执行5秒
        // 服务超时时间为3秒，但是执行了5秒，服务端会把任务执行完的
        // 服务的超时时间，是指如果服务执行时间超过了指定的超时时间则会抛一个warn（例如把修改timeout = 4000）
        try {
            TimeUnit.SECONDS.sleep(5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("执行结束" + name);

        URL url = RpcContext.getContext().getUrl();
        return String.format("%s：%s, Hello, %s", url.getProtocol(), url.getPort(), name);  // 正常访问
    }

}

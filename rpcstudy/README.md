# Dubbo 基本介绍与手写模拟 Dubbo

## 什么是 RPC
RPC（Remote Procedure Call）—远程过程调用，它是一种通过网络从远程计算机程序上请求服务，而不需要了解底层网络技术的协议。也就是说两台服务器A，B，一个应用部署在A服务器上，想要调用B服务器上应用提供的方法，由于不在一个内存空间，不能直接调用，需要通过网络来表达调用的语义和传达调用的数据。

**简单来说，RPC 就是远程方法调用，远程方法调用和本地方法调用是相对的两个概念，本地方法调用指的是进程内部的方法调用，而远程方法调用指的是两个进程内的方法相互调用。实现远程方法调用，基本的就是通过网络，通过传输数据来进行调用。**

RPC 可以基于 HTTP 或者TCP 来传输数据：
* 1. RPC over Http：基于Http协议来传输数据。
* 2. PRC over Tcp：基于Tcp协议（socket）来传输数据。

对于所传输的数据，可以交由RPC的双方来协商定义，但基本都会包括：
* 1. 调用的是哪个类或接口。
* 2. 调用的是哪个方法，方法名和方法参数类型（考虑方法重载）。
* 3. 调用方法的入参。

所以，我们其实可以看到 RPC 的自定义性是很高的，各个公司内部都可以实现自己的一套 RPC 框架，而 Dubbo 就是阿里所开源出来的一套 RPC 框架。

## RPC 工作原理
RPC的设计由Client，Client stub，Network ，Server stub，Server构成。 其中Client就是用来调用服务的，Cient stub是用来把调用的方法和参数序列化的（因为要在网络中传输，必须要把对象转变成字节），Network用来传输这些信息到Server stub， Server stub用来把这些信息反序列化的，Server就是服务的提供者，最终调用的就是Server提供的方法。

![](https://chengzw258.oss-cn-beijing.aliyuncs.com/Article/20210504222856.png)

* 1.Client像调用本地服务似的调用远程服务。
* 2.Client stub接收到调用后，将方法、参数序列化。
* 3.客户端通过网络（socket，http等）将消息发送到服务端。
* 4.Server stub 收到消息后进行解码（将消息对象反序列化）。
* 5.Server stub 根据解码结果调用本地的服务。
* 6.本地服务执行(对于服务端来说是本地执行)并将结果返回给Server stub。
* 7.Server stub将返回结果打包成消息（将结果消息对象序列化）。
* 8.服务端通过sockets将消息发送到客户端。
* 9.Client stub接收到结果消息，并进行解码（将结果消息反序列化）。
* 10.客户端得到最终结果。

## 开源 RPC 框架对比

![](https://chengzw258.oss-cn-beijing.aliyuncs.com/Article/20210505221546.png)

## 什么是Dubbo
目前，官网上是这么介绍的：Apache Dubbo 是一款高性能、轻量级的开源 Java 服务框架。
在几个月前，官网的介绍是：Apache Dubbo 是一款高性能、轻量级的开源 Java RPC框架。

为什么会将 RPC 改为服务？

Dubbo 一开始的定位就是 RPC 框架，专注于两个服务之间的调用。但随着微服务的盛行，除开服务调用之外，Dubbo 也在逐步的涉猎服务治理、服务监控、服务网关等等，所以现在的 Dubbo 目标已经不止是 RPC 框架了，而是想成为和Spring Cloud 类似的一个服务框架。

上面所说的 Dubbo 指的是 Dubbo 框架，另外 Dubbo 还有 Dubbo 协议的含义，Dubbo 框架提供了许许多多的协议实现，例如：dubbo（默认，基于 Netty），rmi， webservice，http，redis 等。

## 手写模拟 Dubbo

![](https://chengzw258.oss-cn-beijing.aliyuncs.com/Article/20210504230932.png)

Provider需要完成以下内容：
* 1.提供服务的接口。
* 2.提供服务实现类。
* 3.将服务注册到注册中心（根据接口可以获取到服务实现类和服务地址）。
* 4.暴露服务：HTTP协议（基于Tomcat）,Dubbo协议（基于Netty）。

Consumer调用服务的时需要完成以下内容：
* 1.去注册中心获取服务信息。
* 2.调用方法时需要提供以下信息，我们把这四个必要的东西构建成一个对象(Invocation对象)：
    * 1.接口名
    * 2.方法名
    * 3.方法参数类型列表
    * 4.方法值参数值列表

github 地址： https://github.com/cr7258/dubbo-lab/tree/master/rpcstudy

### HelloService 服务
添加一个 HelloService 服务的接口，Consumer 在依赖中只需要引用 HelloService 接口：
```java
package com.chengzw.provider.api;

/**
 * 服务的接口
 * @author 程治玮
 * @since 2021/3/30 10:10 下午
 */
public interface HelloService {
    public String sayHello(String name);
}
```

Provider 需要实现该接口：
```java
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
```

### RPC 远程调用模块
HTTP 和 Dubbo 两个远程调用协议都实现了 Protocol接口：
```java
package com.chengzw.framework;

/**
 * HTTP和Dubbo协议都实现该接口
 * 策略模式
 * @author 程治玮
 * @since 2021/3/31 11:17 下午
 */
public interface Protocol {

    void start(URL url);
    String send(URL url,Invocation invocation);
}
```
Provider 和 Consumer 可以通过配置文件来指定使用哪个协议来完成远程调用（主要就是 Invocation 对象的序列号反序列化和方法的处理），而不需要将调用哪个协议写死在代码中。

```java
package com.chengzw.framework;

import com.chengzw.framework.protocol.dubbo.DubboProtocol;
import com.chengzw.framework.protocol.http.HttpProtocol;
import org.springframework.beans.factory.annotation.Value;

/**
 * 读取配置文件决定服务端和客户端使用http还是dubbo协议
 * 工厂模式，解决协议切换的问题
 * @author 程治玮
 * @since 2021/3/31 11:23 下午
 */

public class ProtocolFactory {

    @Value("${protocol}")
    private static String name;

    public static Protocol getProtocol() {
        if (name == null || name.equals("")) name = "http";
        switch (name) {
            case "http":
                return new HttpProtocol();
            case "dubbo":
                return new DubboProtocol();
            default:
                break;
        }
        return new HttpProtocol();

    }
}
```

### 服务注册

使用 Zookeeper 来做服务的注册中心，Provider 将服务（接口名）和 地址端口（URL）注册到注册中心中，Consumer 从注册中心获取服务的信息：

```java
package com.chengzw.framework.register;

import com.chengzw.framework.URL;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.RetryNTimes;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.data.Stat;

import java.util.HashMap;
import java.util.Map;

/**
 * Zookeeper注册中心写入读取服务端信息
 * @author 程治玮
 * @since 2021/3/31 11:39 下午
 */
public class ZookeeperRegister {
    static CuratorFramework client;

    static Map<String, String> UrlCache = new HashMap<>();

    static {
        client = CuratorFrameworkFactory
                .newClient("localhost:2181", new RetryNTimes(3, 1000));
        client.start();

    }

    private static Map<String, String> REGISTER = new HashMap<>();

    //Provider注册服务
    public static void regist(String interfaceName, String implClass, String url) {
        try {
            Stat stat = client.checkExists().forPath(String.format("/dubbo/service/%s", interfaceName));
            if(stat != null){
                client.delete().forPath(String.format("/dubbo/service/%s", interfaceName));
            }
            String result = client.create().creatingParentsIfNeeded().withMode(CreateMode.EPHEMERAL).forPath(String.format("/dubbo/service/%s", interfaceName),(implClass + "::" + url).getBytes());
            System.out.println("Provier服务注册: " + result);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    //获取Provider URL
    public static URL getURL(String interfaceName) {
        URL url = null;
        String urlString = null;
        //先查询缓存
        if (UrlCache.containsKey(interfaceName)) {
            urlString = UrlCache.get(interfaceName);

        } else {
            try {
                byte[] bytes = client.getData().forPath(String.format("/dubbo/service/%s", interfaceName));
                urlString = new String(bytes);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        String host = urlString.split("::")[1].split(":")[0];
        String port = urlString.split("::")[1].split(":")[1];
        return new URL(host,Integer.parseInt(port));
    }

    //获取Provider实现类
    public static Class getImplClass(String interfaceName) throws Exception {
        byte[] bytes = client.getData().forPath(String.format("/dubbo/service/%s", interfaceName));
        String urlString = new String(bytes);
        return Class.forName(urlString.split("::")[0]);
    }
}
```
## 参考链接
* https://dubbo.apache.org/zh/blog/2019/01/07/%E6%B5%85%E8%B0%88-rpc/
* https://segmentfault.com/a/1190000016741532

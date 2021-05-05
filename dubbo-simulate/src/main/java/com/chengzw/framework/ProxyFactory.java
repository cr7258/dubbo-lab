package com.chengzw.framework;

import com.chengzw.framework.register.FileMapRegister;
import com.chengzw.framework.register.ZookeeperRegister;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * 动态代理
 * @author 程治玮
 * @since 2021/3/30 11:48 下午
 */
public class ProxyFactory {

    @SuppressWarnings("unchecked")
    public static <T> T getProxy(Class interfaceClass) {
        return (T) Proxy.newProxyInstance(interfaceClass.getClassLoader(), new Class[]{interfaceClass}, new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                //代理逻辑
                Protocol protocol = ProtocolFactory.getProtocol();
                Invocation invocation = new Invocation(interfaceClass.getName(), method.getName(), method.getParameterTypes(), args);

                //方式一：Consumer从本地文件获取Provider地址
                //URL url = FileMapRegister.getURL(interfaceClass.getName());

                //方式二：Consumer从Zookeeper获取Provider地址
                URL url = ZookeeperRegister.getURL(interfaceClass.getName());

                System.out.println("Consumer选择的Provider地址是: " + url.toString());
                String result = protocol.send(url, invocation);
                return result;
            }
        });
    }
}

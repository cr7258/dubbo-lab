package com.chengzw.framework.protocol.http;

import com.alibaba.fastjson.JSONObject;
import com.chengzw.framework.Invocation;
import com.chengzw.framework.register.ZookeeperRegister;
import org.apache.commons.io.IOUtils;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

/**
 * 服务提供者处理请求的逻辑
 * @author 程治玮
 * @since 2021/3/30 10:33 下午
 */
public class HttpServletHandler {
    public void handler(HttpServletRequest req, HttpServletResponse resp) {
        //服务提供者处理请求的逻辑
        //使用 JSON 反序列化 Invocation 对象
        try {
            Invocation invocation = JSONObject.parseObject(req.getInputStream(), Invocation.class);
            String interfaceName = invocation.getInterfaceName();

            //方式一：从本地文化根据接口名获取服务具体实现类
            //Class implClass = FileMapRegister.getImplClass(interfaceName);

            //方式二：从Zookeeper注册中心获取服务具体实现类
            Class implClass = ZookeeperRegister.getImplClass(interfaceName);
            //获取方法
            Method method = implClass.getMethod(invocation.getMethodName(), invocation.getParamTypes());
            //执行方法
            String result = (String) method.invoke(implClass.newInstance(), invocation.getParams());
            System.out.println("Http服务器收到: " + result.toString());
            //返回结果
            IOUtils.write(result, resp.getOutputStream());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

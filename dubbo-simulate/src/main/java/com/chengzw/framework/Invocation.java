package com.chengzw.framework;

import java.io.Serializable;

/**
 * 服务消费者调用服务提供者的方法时，在网络中传输的对象
 * @author 程治玮
 * @since 2021/3/30 10:40 下午
 */

public class Invocation implements Serializable {

    private String interfaceName;  //接口名
    private String methodName;   //方法名
    private Class[] paramTypes;  //方法参数类型列表
    private Object[] params;     //方法参数值列表


    public Invocation(String interfaceName, String methodName, Class[] paramTypes, Object[] params) {
        this.interfaceName = interfaceName;
        this.methodName = methodName;
        this.paramTypes = paramTypes;
        this.params = params;
    }

    public String getInterfaceName() {
        return interfaceName;
    }

    public void setInterfaceName(String interfaceName) {
        this.interfaceName = interfaceName;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public Object[] getParams() {
        return params;
    }

    public void setParams(Object[] params) {
        this.params = params;
    }

    public Class[] getParamTypes() {
        return paramTypes;
    }

    public void setParamTypes(Class[] paramTypes) {
        this.paramTypes = paramTypes;
    }
}


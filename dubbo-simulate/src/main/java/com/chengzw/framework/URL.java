package com.chengzw.framework;

import java.io.Serializable;

/**
 * URL实体类
 * @author 程治玮
 * @since 2021/3/31 12:01 上午
 */
public class URL implements Serializable {
    private String hostname;
    private Integer port;

    public URL(String hostname, Integer port) {
        this.hostname = hostname;
        this.port = port;
    }

    public String getHostname() {
        return hostname;
    }

    public void setHostname(String hostname) {
        this.hostname = hostname;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    @Override
    public String toString() {
        return "URL{" +
                "hostname='" + hostname + '\'' +
                ", port=" + port +
                '}';
    }
}
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


    public static Class getImplClass(String interfaceName) throws Exception {
        byte[] bytes = client.getData().forPath(String.format("/dubbo/service/%s", interfaceName));
        String urlString = new String(bytes);
        return Class.forName(urlString.split("::")[0]);
    }
}
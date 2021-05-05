package com.chengzw.framework.protocol.http;

import com.alibaba.fastjson.JSONObject;
import com.chengzw.framework.Invocation;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

/**
 * HTTP客户端
 * @author 程治玮
 * @since 2021/3/30 11:35 下午
 */
public class HttpClient {

    public String send(String hostname, Integer port, Invocation invocation){
        //读取用户的配置，哪种 HttpClient 工具
        HttpRequest request = null;
        try {
            request = HttpRequest.newBuilder()
                    .uri(new URI("http",null,hostname,port,"/",null,null))
                    .POST(HttpRequest.BodyPublishers.ofString(JSONObject.toJSONString(invocation)))
                    .build();
            java.net.http.HttpClient client = java.net.http.HttpClient.newHttpClient();
            HttpResponse<String> response = client.send(request,HttpResponse.BodyHandlers.ofString());
            String result = response.body();
            return result;
        } catch (URISyntaxException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}

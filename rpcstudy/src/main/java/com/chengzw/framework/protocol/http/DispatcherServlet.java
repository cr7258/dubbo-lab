package com.chengzw.framework.protocol.http;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 *
 * @author 程治玮
 * @since 2021/3/30 10:30 下午
 */
public class DispatcherServlet extends HttpServlet {

    /**
     * GET 和 POST 请求都会经过 service 方法
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    @Override
    public void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        new HttpServletHandler().handler(req,resp);
    }
}

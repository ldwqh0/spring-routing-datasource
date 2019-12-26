package com.xyyh.rds.datasource;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

public class RequestRoutingDataSource extends AbstractRoutingDataSource {

    private static final Logger log = LoggerFactory.getLogger(RequestRoutingDataSource.class);

    /**
     * 自动注入 HttpServletRequest，需要获取 request.method
     */
    @Autowired
    private HttpServletRequest request;

    @Override
    protected String determineCurrentLookupKey() {
        // 可能系统中某些对dataSource的请求不是通过http请求 而产生的，比如 后台定时任务等，启动启动时的数据初始化等
        // 而 HttpServletRequest 是跟请求相关的，这时尝试获取request就会报错
        // 这里用 try {...} catch {...}捕捉错误，不会导致程序启动中断
        try {
            HttpMethod method = HttpMethod.resolve(this.request.getMethod());
            // 当请求时POST,PUT,DELETE时，返回key为write.否词返回key为read
            switch (method) {
            case POST:
            case PUT:
            case DELETE:
                return "write";
            default:
                return "read";
            }
        } catch (Exception e) {
            log.error("尝试解析 request时出错，或许不是从HttpServletRequest中发起的调用", e);
            return null;
        }
    }
}

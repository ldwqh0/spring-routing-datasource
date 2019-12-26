package com.xyyh.rds.datasource;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import static org.springframework.http.HttpMethod.*;

public class RequestRoutingDataSource extends AbstractRoutingDataSource {

    private static final Logger log = LoggerFactory.getLogger(RequestRoutingDataSource.class);

    /**
     * 自动注入 HttpServletRequest，需要获取 request.method
     */
    @Autowired
    private HttpServletRequest request;

    private Map<HttpMethod, String> keyMap = new HashMap<>();

    public RequestRoutingDataSource() {
        keyMap.put(GET, "read");
        keyMap.put(POST, "write");
        keyMap.put(PUT, "write");
        keyMap.put(DELETE, "write");
    }

    @Override
    protected String determineCurrentLookupKey() {
        // 可能系统中某些对dataSource的请求不是通过 http请求 而产生的，比如 后台定时任务等，启动启动时的数据初始化等
        // 而 HttpServletRequest 是跟请求相关的，这时尝试获取request就会报错
        // 这里用 try {...} catch {...}捕捉错误，不会导致程序启动中断
        try {
            HttpMethod method = HttpMethod.resolve(this.request.getMethod());
            return keyMap.get(method);
        } catch (Exception e) {
            log.error("尝试解析 request时出错，或许不是从HttpServletRequest中发起的调用", e);
            return null;
        }
    }

}

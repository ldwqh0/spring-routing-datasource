package com.xyyh.rds.datasource;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
import static org.springframework.http.HttpMethod.*;

public class RequestRoutingDataSource extends AbstractRoutingDataSource {

    @Autowired(required = false)
    private HttpServletRequest request;

    private Map<HttpMethod, String> keyMap = new HashMap<>();

    public RequestRoutingDataSource() {
        keyMap.put(GET, "read");
        keyMap.put(POST, "write");
        keyMap.put(PUT, "write");
        keyMap.put(DELETE, "write");
    }

    @Override
    protected Object determineCurrentLookupKey() {
        return keyMap.get(getMethod());
    }

    private HttpMethod getMethod() {
        if (request == null) {
            return POST;
        } else {
            return HttpMethod.resolve(request.getMethod());
        }
    }

}

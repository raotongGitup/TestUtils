package com.example.testutils.base.http;

import java.util.Map;

/**
 * 网络抽象层接口
 */
public interface IHttpProcessor {
    /**
     * post请求
     */
    void post(String url, Map<String, Object> map, final HttpCallback callback);

    /**
     * get请求
     */
    void get(String url, Map<String, Object> map, final HttpCallback callback);



}

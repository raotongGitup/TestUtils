package com.example.testutils.base.http;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;

public class HttpHelper implements IHttpProcessor {
    private static HttpHelper instance;

    private HttpHelper() {
    }

    public static HttpHelper getInstance() {
        if (instance == null) {
            synchronized (HttpHelper.class) {
                if (instance == null) {
                    instance = new HttpHelper();
                }
            }

        }

        return instance;

    }

    private static IHttpProcessor mHttpProcessor = null;

    /**
     * 初始化网络框架
     */
    private static void init(IHttpProcessor iHttpProcessor) {
        mHttpProcessor = iHttpProcessor;

    }

    @Override
    public void post(String url, Map<String, Object> map, HttpCallback callback) {
        // final String finaurl = appendParams(url, map);
        mHttpProcessor.post(url, map, callback);

    }

    @Override
    public void get(String url, Map<String, Object> map, HttpCallback callback) {
        final String finaurl = appendParams(url, map);
        mHttpProcessor.get(finaurl, map, callback);


    }

    /**
     * 接口请求的字符串拼接
     */
    public static String appendParams(String url, Map<String, Object> params) {
        if (params == null || params.isEmpty()) {
            return url;
        }
        StringBuilder builder = new StringBuilder();
        if (builder.indexOf("?") <= 0) {
            builder.append("?");
        } else {
            if (!builder.toString().endsWith("?")) {
                builder.append("&");
            }
        }
        for (Map.Entry<String, Object> entry : params.entrySet()) {
            builder.append("&" + entry.getKey())
                    .append("=")
                    .append(encode(entry.getValue().toString()));

        }

        return builder.toString();


    }

    public static String encode(String str) {

        try {
            return URLEncoder.encode(str, "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }

    }
}

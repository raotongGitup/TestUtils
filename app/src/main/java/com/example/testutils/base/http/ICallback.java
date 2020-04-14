package com.example.testutils.base.http;


/**
 * 网络接口回来数据转装
 */
public interface ICallback {
    void onSuccent(String result);

    void onFailure(String e);


}

package com.example.testutils.base.http;


import com.google.gson.Gson;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * 回调接口的实现
 * （json解析）
 */
public abstract class HttpCallback<Result> implements ICallback {

    @Override
    public void onSuccent(String result) {
        Gson gson = new Gson();
        Class<?> clz = analysisClassInfo(this);
        Result resu = (Result) gson.fromJson(result, clz);
        onSuccent(resu);

    }

    public abstract void onSuccent(Result result);

    public abstract void onError(String e);


    @Override
    public void onFailure(String e) {
        onError(e);

    }

    /**
     * 利用反射获取数据类型
     */
    private static Class<?> analysisClassInfo(Object object) {
        Type gentype = object.getClass().getGenericSuperclass();
        Type[] parment = ((ParameterizedType) gentype).getActualTypeArguments();
        return (Class<?>) parment[0];

    }

}

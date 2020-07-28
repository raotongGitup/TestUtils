package com.example.text;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * create at 2020/7/4
 * author raotong
 * Description : 代理类
 */
public class LogProxy implements InvocationHandler {
    //声明被代理类对象
    private Object src;

    //在私有的构造中给成员设置值
    private LogProxy(Object src) {
        this.src = src;
    }

    /**
     * 提供一个静态的方法返回代理对象
     */
    public static Object factory(Object src) {
        Object proxyedObj = //生成被代理类的接口的子类
                Proxy.newProxyInstance(
                        LogProxy.class.getClassLoader(),
                        src.getClass().getInterfaces(),
                        new LogProxy(src));
        return proxyedObj;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        //如果该方法带有Log注解，先执行Logger操作再执行该方法
        if (method.isAnnotationPresent(Log.class)) {
            System.out.println("logger...");
            return method.invoke(src, args);
        } else {
            return method.invoke(src, args);
        }

    }
}

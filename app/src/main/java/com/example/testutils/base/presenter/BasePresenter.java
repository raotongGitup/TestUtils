package com.example.testutils.base.presenter;

import com.example.testutils.base.activity.BaseView;
import com.example.testutils.base.moudle.BaseMoudle;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class BasePresenter<V extends BaseView, M extends BaseMoudle> {

    private V mView, mProxyView;
    private M mMoudle;

    public M getmMoudle() {
        return mMoudle;
    }

    // 绑定
    public void onAttach(final V mView) {
        // 使用动态代理实现代码的统一处理
        // this.mView = mView;
        mProxyView = (V) Proxy.newProxyInstance(mView.getClass().getClassLoader(), mView.getClass().getInterfaces(), new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                if (mView == null) {
                    return null;
                }
                return method.invoke(mView, args);
            }
        });

    }

    // 解绑
    public void onDetach() {

        this.mView = null;

    }

    public V getmView() {
        return mProxyView;
    }
}

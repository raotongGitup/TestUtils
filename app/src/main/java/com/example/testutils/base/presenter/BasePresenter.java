package com.example.testutils.base.presenter;

import com.example.testutils.base.activity.BaseView;
import com.example.testutils.base.moudle.BaseMoudle;

import java.lang.ref.WeakReference;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Proxy;
import java.lang.reflect.Type;

public class BasePresenter<V extends BaseView, M extends BaseMoudle> implements BaseView {

    private WeakReference<V> mWeakReference;
    private V mProxyView;
    private M mMoudle;

    public M getmMoudle() {
        return mMoudle;
    }

    // 绑定
    public void onAttach(final V mView) {
        // 使用动态代理实现代码的统一处理
        this.mWeakReference = new WeakReference<V>(mView);
        //使用动态代理做统一的逻辑判断 aop 思想
        mProxyView = (V) Proxy.newProxyInstance(mView.getClass().getClassLoader(), mView.getClass().getInterfaces(), new InvocationHandler() {
            @Override
            public Object invoke(Object o, Method method, Object[] objects) throws Throwable {
                if (mWeakReference == null || mWeakReference.get() == null) {
                    return null;
                }
                return method.invoke(mWeakReference.get(), objects);
            }
        });
        /**
         * 动态创建moudle 利用发射获取对应moudle
         * */
        Type getype = this.getClass().getGenericSuperclass();
        Type[] params = ((ParameterizedType) getype).getActualTypeArguments();
        try {
            // 最好判断下类型
            mMoudle = (M) ((Class) params[1]).newInstance();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }

    }

    // 解绑 mWeakReference
    public void onDetach() {
        mWeakReference.clear();
        mWeakReference = null;

    }

    public V getmView() {
        return mProxyView;
    }
}

package com.example.testutils.base.activity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewbinding.ViewBinding;

import com.example.testutils.base.proxy.IMvpProxy;
import com.example.testutils.utils.ClassUtils;

import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public abstract class BaseMvpFragment<T extends ViewBinding> extends Fragment implements BaseView {
    private IMvpProxy iMvpProxy;
    public T binding;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = getBinding();
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        iMvpProxy = new IMvpProxy(this);
        iMvpProxy.crAttachPresenter();
        initView();
        initData();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
    }

    protected abstract void initData();

    protected abstract void initView();

//    public abstract int setContentView();

//    /**
//     * 封装findViewById
//     */
//    protected <V extends View> V getView(int viewId) {
//        View view = sparseArray.get(viewId);
//        if (view == null) {
//            view = mRootView.findViewById(viewId);
//            sparseArray.put(viewId, view);
//        } else {
//            view = sparseArray.get(viewId);
//        }
//        return (V) view;
//    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (iMvpProxy != null) {
            iMvpProxy.onDetachPresenter();
        }
    }

    protected T getBinding() {
        try {
            Type superClass = getClass().getGenericSuperclass();
            Type type = ((ParameterizedType) superClass).getActualTypeArguments()[0];
            Class<?> clazz = ClassUtils.getRawType(type);
            Method method = clazz.getMethod("inflate", LayoutInflater.class);
            return (T) method.invoke(null, getLayoutInflater());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}

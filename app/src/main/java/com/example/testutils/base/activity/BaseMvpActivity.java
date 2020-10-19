package com.example.testutils.base.activity;

import android.os.Bundle;
import android.view.LayoutInflater;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewbinding.ViewBinding;

import com.example.testutils.base.proxy.IMvpProxy;
import com.example.testutils.utils.ClassUtils;
import com.gyf.immersionbar.ImmersionBar;

import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public abstract class BaseMvpActivity<T extends ViewBinding> extends AppCompatActivity implements BaseView {

    public T binding;
    private IMvpProxy proxy;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = getBinding();
        setContentView(binding.getRoot());
        if (getStartbar()) {
            ImmersionBar.with(this);
        }
        if (getStartbarColor() > 0) {
            ImmersionBar.with(this)
                    .statusBarColor(getStartbarColor())
                    .init();

        }
        if (statusBarDarkFont()) {
            ImmersionBar.with(this)
                    .statusBarDarkFont(true, 0.2f) //原理：如果当前设备支持状态栏字体变色，会设置状态栏字体为黑色，如果当前设备不支持状态栏字体变色，会使当前状态栏加上透明度，否则不执行透明度
                    .init();
        }

        proxy = new IMvpProxy(this);
        proxy.crAttachPresenter();
        initView();
        initData();
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

    private boolean getStartbar() {
        return true;
    }

    private int getStartbarColor() {
        return 0;
    }

    protected boolean statusBarDarkFont() {
        return true;
    }

    protected abstract void initData();

    protected abstract void initView();

    @Override
    protected void onDestroy() {
        super.onDestroy();
        proxy.onDetachPresenter();


    }
}

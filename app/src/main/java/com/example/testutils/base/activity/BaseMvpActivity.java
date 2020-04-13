package com.example.testutils.base.activity;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.testutils.base.presenter.BasePresenter;
import com.example.testutils.base.proxy.IMvpProxy;
import com.gyf.immersionbar.ImmersionBar;

public abstract class BaseMvpActivity<P extends BasePresenter> extends AppCompatActivity implements BaseView {


    private IMvpProxy proxy;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView();
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

    private boolean getStartbar() {
        return true;
    }

    private int getStartbarColor() {
        return 0;
    }

    protected boolean statusBarDarkFont() {
        return true;
    }

    protected abstract void setContentView();

    protected abstract void initData();

    protected abstract void initView();

    @Override
    protected void onDestroy() {
        super.onDestroy();
        proxy.onDetachPresenter();


    }
}

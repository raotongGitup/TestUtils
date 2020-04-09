package com.example.testutils.base.activity;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.testutils.base.presenter.BasePresenter;
import com.example.testutils.base.proxy.IMvpProxy;

public abstract class BaseMvpActivity<P extends BasePresenter> extends AppCompatActivity implements BaseView {


    private IMvpProxy proxy;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView();
        proxy = new IMvpProxy(this);
        proxy.crAttachPresenter();

        initView();
        initData();

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

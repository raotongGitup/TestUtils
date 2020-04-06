package com.example.testutils.base.activity;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.testutils.base.presenter.BasePresenter;

public abstract class BaseMvpActivity<P extends BasePresenter> extends AppCompatActivity implements BaseView {

    private P mPreaenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPreaenter = createPresenter(); // 创建p
        mPreaenter.onAttach(this);

        initView();
        initData();

    }

    protected abstract P createPresenter();

    protected abstract void initData();

    protected abstract void initView();

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPreaenter.onDetach();
    }
}

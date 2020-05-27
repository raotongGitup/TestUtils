package com.example.testutils.base.activity;

import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import androidx.annotation.Nullable;

import com.example.testutils.R;
import com.example.testutils.base.state.OnNetworkListener;
import com.example.testutils.base.state.OnRetryListener;
import com.example.testutils.base.state.StateLayoutManager;

public abstract class BaseDataStateActivity extends BaseMvpActivity {

    private FrameLayout mContentView;
    protected StateLayoutManager statusLayoutManager;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initStatusLayout();
        initBaseView();

    }

    // 添加布局
    protected void initBaseView() {
        if (getErrorView() != null) {
            getErrorView().addView(statusLayoutManager.getRootLayout());
        }

    }

    protected abstract ViewGroup getErrorView();


    // 初始化布局
    protected void initStatusLayout() {

        statusLayoutManager = StateLayoutManager.newBuilder(this)
                .emptyDataView(R.layout.activity_empty_data)
                .errorView(R.layout.activity_error_data)// 数据异常
                .loadingView(R.layout.activity_loading_data)// loading界面
                .netWorkErrorView(R.layout.activity_networkerror)// 网络异常
                .onRetryListener(new OnRetryListener() {
                    @Override
                    public void onRetry() {
                        //重新加载
                        onRetry();

                    }
                })
                .onNetworkListener(new OnNetworkListener() {
                    @Override
                    public void onNetwork() {
                        // 网络异常重新加载
                        onNetwork();
                    }
                })

                .build();

    }

    // 网络，失败点击重新加载
    protected abstract void onRetry();

    // 网络异常点击
    protected abstract void onNetwork();

    // 正在加载中状态
    protected void showLoading() {
        statusLayoutManager.showLoading();

    }

    protected void hideLoading() {
        statusLayoutManager.goneLoading();
    }

    // 加载数据为空时状态
    protected void showEmptyData() {
        statusLayoutManager.showEmptyData();
    }

    //加载数据错误时状态
    protected void showError() {
        statusLayoutManager.showError();
    }

    //网络错误时状态
    protected void showNetWorkError() {
        statusLayoutManager.showNetWorkError();
    }


}

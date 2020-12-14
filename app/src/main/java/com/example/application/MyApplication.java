package com.example.application;

import android.app.Application;

import com.example.loading.LoadingAndRetryManager;
import com.example.testutils.R;
import com.example.utils.AppContext;

/**
 * create at 2020/6/24
 * author raotong
 * Description : 启动得application
 */
public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        AppContext.getInstance().setAppLication(this);
       // CrashHandler.getInstance().init(this);
        LoadingAndRetryManager.BASE_RETRY_LAYOUT_ID = R.layout.base_retry;
        LoadingAndRetryManager.BASE_LOADING_LAYOUT_ID = R.layout.base_loading;
        LoadingAndRetryManager.BASE_EMPTY_LAYOUT_ID = R.layout.base_empty;


    }
}

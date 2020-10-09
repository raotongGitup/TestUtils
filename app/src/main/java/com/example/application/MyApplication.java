package com.example.application;

import android.app.Application;

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
    }
}

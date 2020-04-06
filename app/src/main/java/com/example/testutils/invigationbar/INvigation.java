package com.example.testutils.invigationbar;

import android.view.View;
import android.view.ViewGroup;

public interface INvigation {
    // 创建布局
    void createNavigationView();

    // 绑定控件
    void attachNagivation(View mNavigationView);

    // 添加布局
    void addNagivation(View mView, ViewGroup parent);


}

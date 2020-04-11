package com.example.testutils.base.state;

import android.view.View;

/**
 * 为状态View显示隐藏监听事件，写成接口
 */
public interface OnShowHideViewListener {
    /**
     * show
     *
     * @param view view
     * @param id   view对应id
     */
    void onShowView(View view, int id);

    /**
     * hide
     *
     * @param view view
     * @param id   view对应id
     */
    void onHideView(View view, int id);
}

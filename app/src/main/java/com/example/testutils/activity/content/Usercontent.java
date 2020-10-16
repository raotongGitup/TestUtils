package com.example.testutils.activity.content;


import com.example.testutils.base.activity.BaseView;

/**
 * create at 2020/10/16
 * author raotong
 * Description :Mvp封装的控制类
 */
public class Usercontent {
    public interface UserInfoView extends BaseView {
        // 正在加载中
        void loading();

        // 请求成功
        void onSuccent(String json);

        // 加载失败
        void onFailure(String erro);

    }

    public interface UserInfoPresenter {
        // 正在加载中


    }


}

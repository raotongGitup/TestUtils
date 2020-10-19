package com.example.testutils.activity.present;

import com.example.testutils.activity.content.Usercontent;
import com.example.testutils.activity.moudle.UserInfoMoudle;
import com.example.testutils.base.http.ICallback;
import com.example.testutils.base.presenter.BasePresenter;

/**
 * create at 2020/10/16
 * author raotong
 * Description :(Usermvp的实例测试使用实例)
 */
public class UserInfoPresenter extends BasePresenter<Usercontent.UserInfoView, UserInfoMoudle> implements Usercontent.UserInfoPresenter {
    @Override
    public void gteToken() {
        // 调用moudle中的接口
        getmMoudle().gteToken(new ICallback() {
            @Override
            public void onSuccent(String result) {
                getmView().onSuccent(result);
            }

            @Override
            public void onFailure(String e) {
                getmView().onFailure(e);

            }
        });

    }
}

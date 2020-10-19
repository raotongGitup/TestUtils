package com.example.testutils.activity;

import android.util.Log;
import android.view.View;

import com.example.testutils.activity.content.Usercontent;
import com.example.testutils.activity.present.UserInfoPresenter;
import com.example.testutils.base.activity.BaseMvpActivity;
import com.example.testutils.base.inject.InjectPresenter;
import com.example.testutils.databinding.ActivityMvpBinding;

public class MvpActivity extends BaseMvpActivity<ActivityMvpBinding> implements Usercontent.UserInfoView{

    private static final String TAG = "MvpActivity";
    @InjectPresenter
    UserInfoPresenter userInfoPresenter;


    @Override
    protected void initData() {
        binding.buttonText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userInfoPresenter.gteToken();
            }
        });

    }

    @Override
    protected void initView() {

    }

    @Override
    public void loading() {

    }

    @Override
    public void onSuccent(String json) {
        Log.e(TAG, "onSuccent: " + json);

    }

    @Override
    public void onFailure(String erro) {

    }
}
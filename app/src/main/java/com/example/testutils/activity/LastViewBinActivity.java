package com.example.testutils.activity;

import android.view.View;

import com.example.testutils.base.activity.BaseMvpActivity;
import com.example.testutils.databinding.ActivityViewBinLastBinding;

public class LastViewBinActivity extends BaseMvpActivity<ActivityViewBinLastBinding> {


    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {
        binding.textButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.textButton.setText("按钮");
            }
        });

    }
}
package com.example.testutils.activity;

import android.view.View;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.testutils.R;
import com.example.testutils.activity.fragment.BlankFragment;
import com.example.testutils.base.activity.BaseMvpActivity;
import com.example.testutils.databinding.ActivityViewBinLastBinding;

public class LastViewBinActivity extends BaseMvpActivity<ActivityViewBinLastBinding> {

    private FragmentManager manager;
    private FragmentTransaction transaction;


    @Override
    protected void initData() {
        manager = getSupportFragmentManager();
        transaction = manager.beginTransaction();
        transaction.add(R.id.fragment_layout, new BlankFragment());


        transaction.commit();


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
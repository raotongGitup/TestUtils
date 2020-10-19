package com.example.testutils.activity.fragment;

import android.view.View;

import androidx.fragment.app.Fragment;

import com.example.testutils.base.activity.BaseMvpFragment;
import com.example.testutils.databinding.FragmentBlankBinding;

/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 * 测试binding的fragment
 */
public class BlankFragment extends BaseMvpFragment<FragmentBlankBinding> {


    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {
        binding.fragmentText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.fragmentText.setText("修改成功");

            }
        });

    }
}
package com.example.testutils.base.activity;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.testutils.base.inject.InjectPresenter;
import com.example.testutils.base.presenter.BasePresenter;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public abstract class BaseMvpActivity<P extends BasePresenter> extends AppCompatActivity implements BaseView {

    private List<BasePresenter> mPreaenters;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPreaenters = new ArrayList<>();
        initActivityParesent();
        initView();
        initData();

    }

    protected void initActivityParesent() {
        Field[] fields = this.getClass().getDeclaredFields();
        for (Field field : fields) {
            InjectPresenter injectPresenter = field.getAnnotation(InjectPresenter.class);
            if (injectPresenter != null) {
                Class<? extends BasePresenter> presenterClass = null;
                if (field.getType().getClass().isAssignableFrom(BasePresenter.class)) {
                    presenterClass = (Class<? extends BasePresenter>) field.getType();
                } else {
                    throw new RuntimeException("请在正确的presenter添加注释" + this.getClass().getName());
                }
                try {
                    BasePresenter basePresenter = presenterClass.newInstance();
                    basePresenter.onAttach(this);
                    field.setAccessible(true);
                    field.set(this, basePresenter);
                    mPreaenters.add(basePresenter);
                } catch (Exception e) {
                    e.printStackTrace();
                }


            }

        }

    }

    protected abstract void initData();

    protected abstract void initView();

    @Override
    protected void onDestroy() {
        super.onDestroy();
        for (BasePresenter presenter : mPreaenters) {
            presenter.onDetach();

        }

    }
}

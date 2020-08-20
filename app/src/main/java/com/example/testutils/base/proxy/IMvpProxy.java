package com.example.testutils.base.proxy;

import com.example.testutils.base.activity.BaseView;
import com.example.testutils.base.inject.InjectPresenter;
import com.example.testutils.base.presenter.BasePresenter;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class IMvpProxy<V extends BaseView> extends ImProxy implements MvpProxyActivity, MvpProxyFragment, MvpProxyViewGroup {

    private V mView;
    private List<BasePresenter> mPreaenters;

    public IMvpProxy(V mView) {
        if (mView != null) {
            this.mView = mView;
            mPreaenters = new ArrayList<>();
        }


    }

    @Override
    public void crAttachPresenter() {
        initActivityParesent();

    }

    @Override
    public void onDetachPresenter() {
        for (BasePresenter presenter : mPreaenters) {
            presenter.onDetach();

        }


    }


    protected void initActivityParesent() {
        Field[] fields = mView.getClass().getDeclaredFields();



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
                    basePresenter.onAttach(mView);
                    field.setAccessible(true);
                    field.set(this, basePresenter);
                    mPreaenters.add(basePresenter);
                } catch (Exception e) {
                    e.printStackTrace();
                }


            }

        }

    }
}

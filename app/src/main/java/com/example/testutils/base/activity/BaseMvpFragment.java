package com.example.testutils.base.activity;

import android.os.Bundle;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.testutils.base.proxy.IMvpProxy;

public abstract class BaseMvpFragment extends Fragment implements BaseView {
    private View mRootView;
    private SparseArray<View> sparseArray = new SparseArray<>();
    private IMvpProxy iMvpProxy;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (mRootView == null) {
            mRootView = LayoutInflater.from(getContext()).inflate(setContentView(), null);
        }
        ViewGroup parent = (ViewGroup) mRootView.getParent();
        if (parent != null) {
            parent.removeView(mRootView);
        }
        return mRootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        iMvpProxy = new IMvpProxy(this);
        iMvpProxy.crAttachPresenter();
        initView();
        initData();
    }

    protected abstract void initData();

    protected abstract void initView();

    public abstract int setContentView();

    /**
     * 封装findViewById
     */
    protected <V extends View> V getView(int viewId) {
        View view = sparseArray.get(viewId);
        if (view == null) {
            view = mRootView.findViewById(viewId);
            sparseArray.put(viewId, view);

        } else {
            view = sparseArray.get(viewId);
        }
        return (V) view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (iMvpProxy != null) {
            iMvpProxy.onDetachPresenter();
        }
    }
}

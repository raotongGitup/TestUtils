package com.example.testutils.invigationbar;

import android.content.Context;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.lang.ref.WeakReference;

/**
 * 基础的使用方式
 */
public class AbsNavigation<B extends AbsNavigation.Builder> implements INvigation {
    private B mBuilder;
    private View mNavigationView;
    private static SparseArray<CharSequence> textArray = new SparseArray<>();
    private static SparseArray<View.OnClickListener> clickArray = new SparseArray<>();
    private SparseArray<WeakReference<View>> weakArray = new SparseArray<>();

    public AbsNavigation(B mBuilder) {
        this.mBuilder = mBuilder;
        // 创建布局
        createNavigationView();

    }

    public B getmBuilder() {
        return mBuilder;
    }

    @Override
    public void createNavigationView() {
        mNavigationView = LayoutInflater.from(mBuilder.mContext).inflate(mBuilder.mlayoutId, mBuilder.parent, false);
        addNagivation(mNavigationView, mBuilder.parent);
        attachNagivation(mNavigationView);
    }

    @Override
    public void attachNagivation(View mNavigationView) {
        for (int i = 0; i < textArray.size(); i++) {
            TextView textView = getView(textArray.keyAt(i), mNavigationView);
            if (textView != null) {
                textView.setText(textArray.valueAt(i));
            }
        }
        for (int i = 0; i < clickArray.size(); i++) {
            View view = getView(clickArray.keyAt(i), mNavigationView);
            if (view != null) {
                view.setOnClickListener(clickArray.valueAt(i));
            }

        }

    }

    @Override
    public void addNagivation(View mView, ViewGroup parent) {
        parent.addView(mView,0);

    }

    public abstract static class Builder<B extends Builder> {
        public Context mContext;
        public int mlayoutId;
        public ViewGroup parent;

        public Builder(Context mContext, int mlayoutId, ViewGroup parent) {
            this.mContext = mContext;
            this.mlayoutId = mlayoutId;
            this.parent = parent;

        }

        public abstract AbsNavigation create();// 创建

        public B setText(int mViewId, CharSequence text) {
            textArray.put(mViewId, text);


            return (B) this;
        }

        public B setOnClicklister(int mViewId, View.OnClickListener listener) {
            clickArray.put(mViewId, listener);

            return (B) this;
        }
    }

    public <T extends View> T getView(int mViewId, View mNavigationView) {
        WeakReference<View> weakReference = weakArray.get(mViewId);
        View view = null;
        if (weakReference != null) {
            view = weakReference.get();
        }

        if (view == null) {
            view = mNavigationView.findViewById(mViewId);
            if (view != null) {
                weakArray.put(mViewId, new WeakReference<View>(view));
            }
        }
        return (T) view;

    }


}

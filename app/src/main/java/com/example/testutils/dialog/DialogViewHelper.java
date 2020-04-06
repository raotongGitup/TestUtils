package com.example.testutils.dialog;

import android.content.Context;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import java.lang.ref.WeakReference;

/**
 * 布局辅助类
 */
public class DialogViewHelper {
    private View mContentView;
    private SparseArray<WeakReference<View>> mView;

    public DialogViewHelper(Context context, int mLayoutId, Window window) {
        this();
        mContentView = LayoutInflater.from(context).inflate(mLayoutId, null);
        window.setContentView(mContentView);


    }

    public void setmContentView(View mView, Window window) {
        this.mContentView = mView;
        window.setContentView(mView);
    }

    public DialogViewHelper() {
        mView = new SparseArray<>();
    }

    private <T extends View> T getView(int mViewId) {
        WeakReference<View> weakReference = mView.get(mViewId);
        View view = null;
        if (weakReference != null) {
            view = weakReference.get();
            if (view == null) {
                view = mContentView.findViewById(mViewId);
                if (view != null) {
                    mView.put(mViewId, new WeakReference<View>(view));

                }
            }

        }
        return (T) view;

    }

    public void setText(int mViewId, CharSequence text) {
        TextView textView = getView(mViewId);
        if (textView != null) {
            textView.setText(text);
        }

    }

    public void setOnclicklister(int mViewId, View.OnClickListener listener) {
        View clickView = getView(mViewId);
        if (clickView != null) {
            clickView.setOnClickListener(listener);
        }

    }
}

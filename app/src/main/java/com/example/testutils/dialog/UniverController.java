package com.example.testutils.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.SparseArray;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;



public class UniverController {
    private Dialog mDialog;
    private Window mWindows;

    public UniverController(Dialog mDialog, Window mWindows) {
        this.mDialog = mDialog;
        this.mWindows = mWindows;
    }

    public static class UniverParams {
        public Context mContext;
        public int mThemResId;
        public boolean mCancelable = true; // 点击空白是否消失
        public DialogInterface.OnCancelListener mOnCancelListener;  // cancel 监听
        public DialogInterface.OnDismissListener mOnDismissListener;
        public DialogInterface.OnKeyListener mOnKeyListener;
        public View mView;
        public int mlayoutId;
        public SparseArray<CharSequence> textArray = new SparseArray<>();
        public SparseArray<View.OnClickListener> onClickArray = new SparseArray<>();
        public int mWith = ViewGroup.LayoutParams.WRAP_CONTENT;
        public int mHigh = ViewGroup.LayoutParams.WRAP_CONTENT;
        public int mGravity = Gravity.CENTER;
        public int mAnimation = 0;

        public UniverParams(Context mContext, int mThemResId) {
            this.mContext = mContext;
            this.mThemResId = mThemResId;
        }

        DialogViewHelper viewHelper = null;

        public void apply(UniverController mUniver) {
            /**
             * 绑定数据
             * */
            if (mView != null) {
                viewHelper = new DialogViewHelper();
                viewHelper.setmContentView(mView, mUniver.mWindows);
            }
            if (mlayoutId != 0) {
                viewHelper = new DialogViewHelper(mContext, mlayoutId, mUniver.mWindows);
            }
            if (viewHelper == null) {
                if (viewHelper == null) {
                    throw new IllegalArgumentException("请设置布局方法为setContentView");
                }
            }
            /**
             * 设置必要参数
             * */
            mUniver.mDialog.setCancelable(mCancelable);
            if (mCancelable) {
                mUniver.mDialog.setCanceledOnTouchOutside(true);
            }
            mUniver.mDialog.setOnCancelListener(mOnCancelListener);
            if (mOnDismissListener != null) {
                mUniver.mDialog.setOnDismissListener(mOnDismissListener);
            }

            if (mOnKeyListener != null) {
                mUniver.mDialog.setOnKeyListener(mOnKeyListener);
            }
            WindowManager.LayoutParams params = mUniver.mWindows.getAttributes();
            params.width = mWith;
            params.height = mHigh;
            params.gravity = mGravity;
            mUniver.mWindows.setAttributes(params);
            if (mAnimation != 0) {
                mUniver.mWindows.setWindowAnimations(mAnimation);
            }

            for (int i = 0; i < textArray.size(); i++) {
                viewHelper.setText(textArray.keyAt(i), textArray.valueAt(i));
            }
            for (int i = 0; i < onClickArray.size(); i++) {
                viewHelper.setOnclicklister(onClickArray.keyAt(i), onClickArray.valueAt(i));
            }


        }
    }


}

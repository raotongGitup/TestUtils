package com.example.testutils.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
/**
 * 万能的dialog的类封装
 */
import com.example.testutils.R;

public class UniverDialog extends Dialog {
    private UniverController mUniver;

    private UniverDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
        mUniver = new UniverController(this, getWindow());
    }


    public static class Builder {
        private UniverController.UniverParams P;

        public Builder(Context mContext) {
            this(mContext, R.style.dialog);
        }

        public Builder(Context mContext, int mThemResId) {
            P = new UniverController.UniverParams(mContext, mThemResId);
        }

        public Builder setContentView(View view) {
            P.mView = view;
            P.mlayoutId = 0;
            return this;

        }

        public Builder setContentView(int mlayoutId) {
            P.mView = null;
            P.mlayoutId = mlayoutId;
            return this;

        }

        public Builder setText(int mViewId, CharSequence text) {
            P.textArray.put(mViewId, text);
            return this;
        }

        public Builder setOnclicklister(int mViewId, View.OnClickListener listener) {
            P.onClickArray.put(mViewId, listener);
            return this;
        }

        public Builder setCancelable(boolean mCancelable) {
            P.mCancelable = mCancelable;
            return this;
        }

        public Builder setOnCancelListener(DialogInterface.OnCancelListener mOnCancelListener) {
            P.mOnCancelListener = mOnCancelListener;
            return this;
        }

        public Builder setOnDismissListener(DialogInterface.OnDismissListener mOnDismissListener) {
            P.mOnDismissListener = mOnDismissListener;
            return this;
        }

        public Builder setOnKeyListener(DialogInterface.OnKeyListener mOnKeyListener) {
            P.mOnKeyListener = mOnKeyListener;
            return this;
        }

        public Builder fullScreenWith() {
            P.mWith = ViewGroup.LayoutParams.MATCH_PARENT;
            P.mHigh = ViewGroup.LayoutParams.MATCH_PARENT;
            return this;
        }

        private Builder setWithAndHight(int mWith, int mHight) {
            P.mWith = mWith;
            P.mHigh = mHight;
            return this;
        }

        public Builder fullBottom(boolean flag) {
            if (flag) {
                P.mAnimation = R.style.dialogWindowAnim;
            }
            P.mGravity = Gravity.BOTTOM;
            return this;
        }

        public Builder setAnimation(int animation) {
            P.mAnimation = animation;
            return this;
        }

        public Builder setGravity(int mGravity) {
            P.mGravity = mGravity;
            return this;
        }

        public UniverDialog create() {
            UniverDialog dialog = new UniverDialog(P.mContext, P.mThemResId);
            P.apply(dialog.mUniver);
            dialog.setCancelable(P.mCancelable);
            if (P.mCancelable) {
                dialog.setCanceledOnTouchOutside(true);
            }
            dialog.setOnCancelListener(P.mOnCancelListener);
            dialog.setOnDismissListener(P.mOnDismissListener);
            if (P.mOnKeyListener != null) {
                dialog.setOnKeyListener(P.mOnKeyListener);
            }

            return dialog;
        }

        public UniverDialog show() {
            final UniverDialog dialog = create();
            dialog.show();
            return dialog;


        }
    }
}

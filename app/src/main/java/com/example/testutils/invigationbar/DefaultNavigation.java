package com.example.testutils.invigationbar;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.testutils.R;

/**
 * 默认的导航栏 样式封装
 */
public class DefaultNavigation extends AbsNavigation<DefaultNavigation.Builder> {

    public DefaultNavigation(Builder mBuilder) {
        super(mBuilder);
    }

    @Override
    public void attachNagivation(View mNavigationView) {
        super.attachNagivation(mNavigationView);
        TextView lefttext = mNavigationView.findViewById(R.id.left_text);
        lefttext.setVisibility(getmBuilder().leftVisable);
        lefttext.setTextColor(getmBuilder().leftColor);
        lefttext.setTypeface(Typeface.defaultFromStyle(getmBuilder().leftTextStype));
        RelativeLayout relativeLayout = mNavigationView.findViewById(R.id.background_rl);
        relativeLayout.setBackgroundColor(getmBuilder().mBackGroungColor);
        ImageView left_icon = mNavigationView.findViewById(R.id.left_icon);
        left_icon.setVisibility(getmBuilder().leftIconVisable);
        TextView centerTv = mNavigationView.findViewById(R.id.center_text);
        centerTv.setVisibility(getmBuilder().centerTextVisable);
        centerTv.setTextColor(getmBuilder().centerTextColor);
        centerTv.setTypeface(Typeface.defaultFromStyle(getmBuilder().centerTextStype));
        TextView rightText = mNavigationView.findViewById(R.id.right_text);
        rightText.setVisibility(getmBuilder().rightTextVisable);
        rightText.setTextColor(getmBuilder().rightTextColor);
        rightText.setTypeface(Typeface.defaultFromStyle(getmBuilder().rightTextStype));
        ImageView rightIcon = mNavigationView.findViewById(R.id.right_icon);
        rightIcon.setVisibility(getmBuilder().rightIconVisable);
        if (getmBuilder().leftIcon != 0) {
            left_icon.setImageResource(getmBuilder().leftIcon);
        }
        if (getmBuilder().rightIcon != 0) {
            rightIcon.setImageResource(getmBuilder().rightIcon);
        }


    }

    public static class Builder extends AbsNavigation.Builder<DefaultNavigation.Builder> {
        private int leftVisable = View.GONE;
        private int leftColor = Color.BLACK;
        private int leftTextStype = Typeface.NORMAL;
        private int mBackGroungColor = Color.WHITE;
        private int leftIcon;
        private int leftIconVisable = View.VISIBLE;
        private int centerTextVisable = View.VISIBLE;
        private int centerTextColor = Color.BLACK;
        private int rightTextVisable = View.VISIBLE;
        private int rightTextColor = Color.BLACK;
        private int rightIcon;
        private int rightIconVisable = View.GONE;
        private int centerTextStype = Typeface.NORMAL;
        private int rightTextStype = Typeface.NORMAL;

        public Builder(Context mContext, ViewGroup parent) {
            super(mContext, R.layout.default_nagivation_bar, parent);
        }

        @Override
        public DefaultNavigation create() {
            return new DefaultNavigation(this);
        }

        public Builder setLeftText(CharSequence text) {
            setText(R.id.left_text, text);
            return this;
        }

        public Builder setleftClickLister(View.OnClickListener listener) {
            setOnClicklister(R.id.left_text, listener);
            return this;
        }

        public Builder hideLeftText(boolean flag) {
            if (flag) {
                leftVisable = View.VISIBLE;
            } else {
                leftVisable = View.GONE;
            }
            return this;
        }

        public Builder setLeftTextClor(int color) {
            leftColor = color;
            return this;
        }

        public Builder setLeftTextStyle(int textStyle) {
            this.leftTextStype = textStyle;
            return this;

        }

        public Builder setBackGroungColor(int GroungColor) {
            mBackGroungColor = GroungColor;
            return this;
        }

        public Builder setleftIcon(int leftIcon) {
            this.leftIcon = leftIcon;
            return this;
        }

        public Builder hideLeftIcon(boolean flag) {
            if (flag) {
                this.leftIconVisable = View.VISIBLE;
            } else {
                this.leftIconVisable = View.GONE;
            }

            return this;
        }

        public Builder setleftIconOncliclLister(View.OnClickListener listener) {
            setOnClicklister(R.id.left_icon, listener);
            return this;
        }

        public Builder setCenterText(CharSequence text) {
            setText(R.id.center_text, text);
            return this;
        }

        public Builder hideCenterText(boolean flag) {
            if (flag) {
                this.centerTextVisable = View.VISIBLE;
            } else {
                this.centerTextVisable = View.GONE;
            }
            return this;
        }

        public Builder setCenterTextColor(int color) {
            this.centerTextColor = centerTextColor;
            return this;
        }

        public Builder setCenterTextStype(int textStype) {
            this.centerTextStype = textStype;
            return this;
        }

        public Builder setRightText(CharSequence text) {
            setText(R.id.right_text, text);
            return this;
        }

        public Builder hiheRightText(boolean flag) {
            if (flag) {
                this.rightTextVisable = View.VISIBLE;
            } else {
                this.rightTextVisable = View.GONE;
            }
            return this;
        }

        public Builder setRightColor(int color) {
            this.rightTextColor = color;

            return this;
        }

        public Builder setRightTextStyle(int textStyle) {
            this.rightTextStype = textStyle;
            return this;
        }

        public Builder setRightTextOnClicklister(View.OnClickListener listener) {
            setOnClicklister(R.id.center_text, listener);
            return this;
        }

        public Builder setRightIcon(int rightIcon) {
            this.rightIcon = rightIcon;
            return this;
        }

        public Builder hiheRightIconVisable(boolean flag) {
            if (flag) {
                this.rightIconVisable = View.VISIBLE;
            } else {
                this.rightIconVisable = View.GONE;
            }

            return this;
        }

        public Builder setRightIconOnClickLister(View.OnClickListener lister) {
            setOnClicklister(R.id.right_icon, lister);
            return this;
        }


    }
}

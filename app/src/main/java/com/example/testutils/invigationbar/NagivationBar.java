package com.example.testutils.invigationbar;

import android.content.Context;
import android.view.ViewGroup;

/**
 * 供外部使用的NagivationBar(万能的导航栏)
 */
public class NagivationBar extends AbsNavigation {

    public NagivationBar(Builder mBuilder) {
        super(mBuilder);
    }

    public static class Builder extends AbsNavigation.Builder<NagivationBar.Builder> {
        public Builder(Context mContext, int mlayoutId, ViewGroup parent) {
            super(mContext, mlayoutId, parent);
        }

        @Override
        public NagivationBar create() {
            return new NagivationBar(this);
        }
    }
}

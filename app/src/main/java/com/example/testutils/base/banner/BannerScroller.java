package com.example.testutils.base.banner;

import android.content.Context;
import android.view.animation.Interpolator;
import android.widget.Scroller;

public class BannerScroller extends Scroller {
    private static  int DEFAULT_DURATION = BannerConfig.DEFAULT_DURATION;

    public BannerScroller(Context context) {
        this(context, null);
    }

    public BannerScroller(Context context, Interpolator interpolator) {
        super(context, interpolator);
    }

    @Override
    public void startScroll(int startX, int startY, int dx, int dy) {
        super.startScroll(startX, startY, dx, dy, DEFAULT_DURATION);
    }

    @Override
    public void startScroll(int startX, int startY, int dx, int dy, int duration) {
        super.startScroll(startX, startY, dx, dy, DEFAULT_DURATION);
    }


}

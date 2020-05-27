package com.example.testutils.base.banner;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.ViewPager;

/**
 * 半透明+缩放(banner透明加缩放动画)
 */
public class GalleryTransformer implements ViewPager.PageTransformer {
    private static final float MAX_ALPHA = 0.5f;
    private static final float MAX_SCALE = 0.9f;

    @Override
    public void transformPage(@NonNull View page, float position) {
        if (position < -1 || position > 1) {
            page.setAlpha(MAX_ALPHA);
            page.setScaleX(MAX_SCALE);
            page.setScaleY(MAX_SCALE);
        } else {
            if (position <= 0) {

                page.setAlpha(MAX_ALPHA + MAX_ALPHA * (1 + position));
            } else {

                page.setAlpha(MAX_ALPHA + MAX_ALPHA * (1 - position));
            }

            float scale = Math.max(MAX_SCALE, 1 - Math.abs(position));
            page.setScaleX(scale);
            page.setScaleY(scale);
        }

    }
}

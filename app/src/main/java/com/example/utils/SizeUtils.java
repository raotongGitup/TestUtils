package com.example.utils;

import android.util.DisplayMetrics;

/**
 * create at 2020/9/7
 * author raotong
 * Description : dp和px转换的工具类
 */
public class SizeUtils {
    /**
     * 屏幕宽
     *
     * @return
     */
    public static int getScreenWith() {
        DisplayMetrics displayMetrics = AppContext.getInstance().getApplicat().getResources().getDisplayMetrics();
        int widthPixels = displayMetrics.widthPixels;
        return widthPixels;
    }

    /**
     * 屏幕高
     *
     * @return
     */
    public static int getScreenHeight() {
        DisplayMetrics displayMetrics = AppContext.getInstance().getApplicat().getResources().getDisplayMetrics();
        int heightPixels = displayMetrics.heightPixels;
        return heightPixels;
    }

    /**
     * dp 转换 px
     *
     * @param dp
     * @return
     */
    public static int dp2px(int dp) {
        float density = AppContext.getInstance().getApplicat().getResources().getDisplayMetrics().density;
        return (int) (dp * density + 0.5f);
    }

    /**
     * ps 转换 px
     *
     * @param px
     * @return
     */
    public static int px2dp(int px) {
        float density = AppContext.getInstance().getApplicat().getResources().getDisplayMetrics().density;
        return (int) (px / density + 0.5f);
    }

    /**
     * convert sp to its equivalent px
     * <p>
     * 将sp转换为px
     */
    public static int sp2px(float spValue) {
        final float fontScale = AppContext.getInstance().getApplicat().getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }
}

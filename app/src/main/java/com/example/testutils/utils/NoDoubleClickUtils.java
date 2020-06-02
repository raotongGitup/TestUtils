package com.example.testutils.utils;

/**
 * create at 2020/6/2
 * author raotong
 * Description : 防止按钮两次点击
 */
public class NoDoubleClickUtils {
    private final static int SPACE_TIME = 500;//2次点击的间隔时间，单位ms
    private static long lastClickTime;

    // 防止多线程操作该类
    public synchronized static boolean isDoubleClick() {
        long currentTime = System.currentTimeMillis();
        boolean isClick;
        if (currentTime - lastClickTime > SPACE_TIME) {
            isClick = false;
        } else {
            isClick = true;
        }
        lastClickTime = currentTime;
        return isClick;
    }

}

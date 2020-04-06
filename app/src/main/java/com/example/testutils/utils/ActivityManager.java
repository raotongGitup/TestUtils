package com.example.testutils.utils;


import android.app.Activity;

import java.util.Stack;

/**
 * activity的管理类（单利模式的使用）
 *
 * @raotong
 **/
public class ActivityManager {
    private static volatile ActivityManager activityManager;

    private Stack<Activity> mactivity;

    private ActivityManager() {
        mactivity = new Stack<Activity>();
    }

    public static ActivityManager getActivityManagerInstance() {
        if (activityManager == null) {
            synchronized (ActivityManager.class) {
                if (activityManager == null) {
                    activityManager = new ActivityManager();
                }
            }

        }
        return activityManager;
    }

    /**
     * 添加activity
     */
    public void onAttach(Activity activity) {
        mactivity.add(activity);

    }

    /**
     * 解绑，防止内存泄漏
     */
    public void onDttach(Activity activity) {
        int siaze = mactivity.size();
        for (int i = 0; i < siaze; i++) {
            if (mactivity.get(i) == activity) {
                mactivity.remove(i);
                i--;
                siaze--;
            }
        }

    }


    public void onFinish(Activity activity) {
        int size = mactivity.size();
        for (int i = 0; i < size; i++) {
            if (mactivity.get(i) == activity) {
                activity.finish();
                mactivity.remove(i);
                i--;
                size--;
            }

        }

    }

    /**
     * 根据类名关闭activity
     */
    public void onFinishName(Class<? extends Activity> activityName) {
        int size = mactivity.size();
        for (int i = 0; i < size; i++) {
            if (mactivity.get(i).getClass().getCanonicalName().equals(activityName.getCanonicalName())) {
                mactivity.get(i).finish();
                mactivity.remove(i);
                i--;
                size--;

            }

        }

    }

    /**
     * 获取当前最上面的activity
     */

    public Activity onTopActivity() {
        int size = mactivity.size();
        if (size == 0) {
            return null;
        }
        return mactivity.get(size - 1);

    }

    /**
     * 退出app
     */
    public void getApplication() {
        System.exit(0);
    }

}

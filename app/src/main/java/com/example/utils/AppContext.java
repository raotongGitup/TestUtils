package com.example.utils;

import android.app.Application;

/**
 * create at 2020/9/7
 * author raotong
 * Description : Application获取的对象
 */
public class AppContext {
    private static volatile AppContext appContext;
    private Application applicat;

    private AppContext() {

    }

    public static AppContext getInstance() {
        if (appContext == null) {
            synchronized (AppContext.class) {
                if (appContext == null) {
                    appContext = new AppContext();
                }
            }
        }


        return appContext;
    }

    public void setAppLication(Application applicat) {
        this.applicat = applicat;
    }

    public Application getApplicat() {
        return applicat;
    }
}

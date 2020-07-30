package com.example.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;

import androidx.annotation.Nullable;

import com.example.testutils.IMyAidlInterface;

/**
 * create at 2020/7/29
 * author raotong
 * Description : AIDL用于进程间通信
 */
public class AIDLService extends Service {

    private String typeName;

    IMyAidlInterface.Stub stub = new IMyAidlInterface.Stub() {
        @Override
        public void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat, double aDouble, String aString) throws RemoteException {

        }

        @Override
        public String getName(String nickname) throws RemoteException {
            return "aidi" + nickname;
        }

        @Override
        public void setName(String name) throws RemoteException {
            // 添加数据
            typeName = name;
        }
    };

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return stub;
    }
}

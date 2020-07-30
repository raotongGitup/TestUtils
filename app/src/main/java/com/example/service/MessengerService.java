package com.example.service;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * create at 2020/7/29
 * author raotong
 * Description : 实现多进程间的通问题
 */
public class MessengerService extends Service {

    class IncomingHandler extends Handler {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:

                    break;
            }

        }
    }

    Messenger messenger = new Messenger(new IncomingHandler());


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return messenger.getBinder();
    }
}

package com.example.testutils.activity;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.service.MessengerService;
import com.example.testutils.R;


/**
 * 用于进程间通信的实验
 */
public class MessengerActivity extends AppCompatActivity {
    private boolean mBound;
    private Messenger mMessenger;


    private ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mMessenger = new Messenger(service);
            mBound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            mBound = false;

        }
    };

    public void sayHello(View view) {
        if (!mBound) {
            return;
        }

        try {

            // 发送进程消息(单线程，不考虑线程安全问题)
            Message message = Message.obtain(null, 0, 0, 0);
            mMessenger.send(message);
        } catch (RemoteException e) {
            e.printStackTrace();
        }

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messenger);
        Intent intent = new Intent(this, MessengerService.class);
        bindService(intent, serviceConnection, BIND_AUTO_CREATE);

    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mBound) {
            unbindService(serviceConnection);
            mBound = false;
        }
    }
}

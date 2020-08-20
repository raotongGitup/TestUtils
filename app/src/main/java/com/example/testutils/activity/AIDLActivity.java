package com.example.testutils.activity;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.service.AIDLService;
import com.example.testutils.IMyAidlInterface;
import com.example.testutils.R;

public class AIDLActivity extends AppCompatActivity {

    private IMyAidlInterface mIMyAidlInterface;

    ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mIMyAidlInterface = IMyAidlInterface.Stub.asInterface(service);

        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aidl);
        Intent intent = new Intent(this, AIDLService.class);

        bindService(intent, serviceConnection, BIND_AUTO_CREATE);
        ((Button) findViewById(R.id.aidi_button)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mIMyAidlInterface != null) {
                    try {
                        String mImAidi = mIMyAidlInterface.getName("数据");
                        Toast.makeText(AIDLActivity.this, mImAidi, Toast.LENGTH_SHORT).show();


                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }

                }

            }
        });
    }
}

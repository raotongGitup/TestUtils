package com.example.testutils.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.LruCache;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.testutils.R;

public class TestActivity extends AppCompatActivity {

    Handler handler = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        Message message = Message.obtain();

        long sty = Runtime.getRuntime().totalMemory();
        System.out.println("----------------------" + sty / 1024);

        LruCache<String, String> lruCache = new LruCache<>(1024 * 1024);

    }

    public void banner(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);

    }

    public void camer(View view) {
        Intent intent = new Intent(this, CameraActivity.class);
        startActivity(intent);

    }

    public void h5(View view) {
        Intent intent = new Intent(this, WebviewActivity.class);
        startActivity(intent);

    }

    public void messager(View view) {
        Intent intent = new Intent(this, MessengerActivity.class);
        startActivity(intent);

    }

    public void aIdl(View view) {
        Intent intent = new Intent(this, AIDLActivity.class);
        startActivity(intent);

    }

    public void weixin(View view) {
        Intent intent = new Intent(this, WeiXinActivity.class);
        startActivity(intent);

    }

    public void viewpage(View view) {
        Intent intent = new Intent(this, ViewpageActivity.class);
        startActivity(intent);
    }

    public void viewBind(View view) {
        Intent intent = new Intent(this, LastViewBinActivity.class);
        startActivity(intent);
    }

    public void MvPView(View view) {
        Intent intent = new Intent(this, MvpActivity.class);
        startActivity(intent);
    }

    public void Error(View view) {
        Intent intent = new Intent(this, OnErrorActivity.class);
        startActivity(intent);
    }

}

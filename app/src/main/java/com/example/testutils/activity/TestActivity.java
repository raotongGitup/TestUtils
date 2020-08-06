package com.example.testutils.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.testutils.R;

public class TestActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
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


}

package com.example.testutils.activity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.testutils.R;

/**
 * 需要跳转的类
 */

public class ProxyActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_proxy);
    }
}

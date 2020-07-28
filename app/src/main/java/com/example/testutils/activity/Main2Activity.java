package com.example.testutils.activity;

import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.testutils.R;
import com.example.testutils.aop.BehaviorTrace;

import java.text.SimpleDateFormat;

public class Main2Activity extends AppCompatActivity {

    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd  HH:mm:ss");
    private String TAG = "jason";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

    }


//    public void mSnke(View view) {
//        Log.e(TAG, "摇一摇，使用时间 " + sdf.format(new Date()));
//        long begin = System.currentTimeMillis();
//        SystemClock.sleep(3000);
//        System.out.println("摇一摇，摇到一个人");
//
//        Log.e(TAG, "消耗时间" + (System.currentTimeMillis() - begin) + "ms");
//
//
//    }
//
//    public void mAudio(View view) {
//        Log.e(TAG, "语音匹配，使用时间 " + sdf.format(new Date()));
//        long begin = System.currentTimeMillis();
//        SystemClock.sleep(3000);
//        System.out.println("语音匹配，匹配到一个人");
//        Log.e(TAG, "消耗时间" + (System.currentTimeMillis() - begin) + "ms");
//
//    }


    @BehaviorTrace("摇一摇功能")
    public void mSnke(View view) {
        SystemClock.sleep(3000);
        System.out.println("摇一摇，摇到一个人");




    }

    @BehaviorTrace("语音匹配功能")
    public void mAudio(View view) {
        SystemClock.sleep(3000);
        System.out.println("语音匹配，匹配到一个人");


    }

    public void mVideo(View view) {


    }

    public void mFriends(View view) {


    }


}

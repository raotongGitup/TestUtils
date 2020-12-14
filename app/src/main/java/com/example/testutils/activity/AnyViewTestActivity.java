package com.example.testutils.activity;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.loading.LoadingAndRetryManager;
import com.example.loading.OnLoadingAndRetryListener;
import com.example.testutils.R;
import com.example.testutils.base.activity.BaseMvpActivity;
import com.example.testutils.databinding.ActivityAnyViewTestBinding;

public class AnyViewTestActivity extends BaseMvpActivity<ActivityAnyViewTestBinding> {

    private TextView idtextview;
    LoadingAndRetryManager mLoadingAndRetryManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // setContentView(R.layout.activity_any_view_test);
        // idtextview = ((TextView) findViewById(R.id.id_textview));
        mLoadingAndRetryManager = LoadingAndRetryManager.generate(binding.idTextview, new OnLoadingAndRetryListener() {
            @Override
            public void setRetryEvent(View retryView) {
                retryRefreashTextView(retryView);
            }
            //generateLoadingLayoutId



            @Override
            public View generateLoadingLayout() {
                View view = LayoutInflater.from(AnyViewTestActivity.this).inflate(R.layout.layout_loading_view, null);
                ImageView imageView = view.findViewById(R.id.image_view);
                ObjectAnimator oaY = ObjectAnimator.ofFloat(imageView, "rotationY", 0, 360);
                oaY.setRepeatCount(ValueAnimator.INFINITE);
                oaY.setDuration(5000);
                oaY.start();
                return view;
            }
        });
//
//
//        refreashTextView();
    }

    @Override
    protected void initData() {
        showLoading();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                closeLoading();


            }
        }).start();


    }

    @Override
    protected void initView() {


    }

    private void refreashTextView() {
        mLoadingAndRetryManager.showLoading();

        new Thread() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (Math.random() > 0.6) {
                    mLoadingAndRetryManager.showContent();
                } else {
                    mLoadingAndRetryManager.showRetry();
                }


            }
        }.start();


    }

    public void retryRefreashTextView(View retryView) {
        View view = retryView.findViewById(R.id.id_btn_retry);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(AnyViewTestActivity.this, "retry event invoked", Toast.LENGTH_SHORT).show();
                AnyViewTestActivity.this.refreashTextView();
            }
        });
    }
}
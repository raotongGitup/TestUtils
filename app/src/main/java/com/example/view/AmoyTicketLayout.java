package com.example.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * create at 2020/8/21
 * author raotong
 * Description : 仿拼多多的头像移动动画
 */
public class AmoyTicketLayout extends FrameLayout {
    public AmoyTicketLayout(@NonNull Context context) {
        this(context, null);
    }

    public AmoyTicketLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public AmoyTicketLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        initView(context);
    }

    private void initView(Context context) {


    }


}

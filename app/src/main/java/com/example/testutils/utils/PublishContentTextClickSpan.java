package com.example.testutils.utils;

import android.content.Context;
import android.text.TextPaint;
import android.text.style.ClickableSpan;
import android.view.View;
import android.widget.Toast;

/**
 * author  raotong
 * Data2020/8/6
 **/
public class PublishContentTextClickSpan extends ClickableSpan {
    private Context mContext ;
    public PublishContentTextClickSpan(Context context) {
        this.mContext = context;
    }

    @Override
    public void onClick(View widget) {
        Toast.makeText(mContext, "点了可点击链接", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void updateDrawState(TextPaint ds) {
        ds.setUnderlineText(false);
    }
}

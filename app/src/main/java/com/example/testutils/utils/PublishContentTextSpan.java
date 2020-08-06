package com.example.testutils.utils;

import android.text.TextPaint;
import android.text.style.MetricAffectingSpan;

/**
 * author  raotong
 * Data2020/8/6
 **/
public class PublishContentTextSpan extends MetricAffectingSpan {
    private String showText;
    private long userId;
    public PublishContentTextSpan(String showText) {
        this.showText = showText;
    }


    public String getShowText() {
        return showText;
    }

    @Override
    public void updateMeasureState(TextPaint p) {

    }

    @Override
    public void updateDrawState(TextPaint tp) {

    }
}

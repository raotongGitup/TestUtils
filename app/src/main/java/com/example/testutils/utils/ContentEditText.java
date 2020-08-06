package com.example.testutils.utils;

import android.content.Context;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatEditText;
import androidx.core.content.ContextCompat;

import com.example.testutils.R;

/**
 * author  raotong
 * Data2020/8/6
 **/
public class ContentEditText extends AppCompatEditText {
    public ContentEditText(Context context) {
        super(context);
    }

    public ContentEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ContentEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /**
     * 添加一个块,在文字的后面添加
     *
     * @param maskText 内容签名的标签
     * @param showText 显示到界面的内容
     */
    public void addAtSpan(String maskText, String showText) {
        StringBuilder builder = new StringBuilder();
        builder.delete(0,builder.length());
        if (!TextUtils.isEmpty(maskText)) {
            //已经添加了#
            builder.append(maskText).append(showText).append(" ");
        } else {
            builder.append(showText).append(" ");
        }
        // 插在光标之后
//        getText().insert(getSelectionStart(), builder.toString());
        // 插在第一位
        getText().insert(0, builder.toString());
        SpannableString sps = new SpannableString(getText());
        // 拼接在中间
//        int start = getSelectionEnd() - builder.toString().length() - (TextUtils.isEmpty(maskText) ? 1 : 0);
        // 在光标末尾
//        int end = getSelectionEnd();
        int start = 0;
        int end = builder.toString().length();
        makeSpan(sps, new UnSpanText(start, end, builder.toString()));
        setText(sps);
        setSelection(end);
        builder.setLength(0);
    }
    /**
     * @param maskText #
     * @param showText 块内容
     * @param afterText 块之后的内容
     */
    public void addAtSpan(String maskText, String showText, String afterText) {
        StringBuilder builder = new StringBuilder();
        builder.delete(0,builder.length());
        if (!TextUtils.isEmpty(maskText)) {
            //已经添加了@
            builder.append(maskText).append(showText).append(" ");
        } else {
            builder.append(showText).append(" ");
        }

        builder.append(afterText);
        SpannableString sps = new SpannableString(builder);

        int start = 0;
        int end = builder.toString().length();
        makeSpan(sps, new UnSpanText(start, showText.length()+1, showText));

        setText(sps);
        setSelection(end);
        builder.setLength(0);
    }

    @Override
    protected void onTextChanged(CharSequence text, int start, int lengthBefore, int lengthAfter) {
        super.onTextChanged(text, start, lengthBefore, lengthAfter);
        //向前删除一个字符，@后的内容必须大于一个字符，可以在后面加一个空格
        if (lengthBefore == 1 && lengthAfter == 0) {
            PublishContentTextSpan[] spans = getText().getSpans(0, getText().length(), PublishContentTextSpan.class);
            for (PublishContentTextSpan publishContentTextSpan : spans) {
                if (getText().getSpanEnd(publishContentTextSpan) == start && !text.toString().endsWith(publishContentTextSpan.getShowText())) {
                    getText().delete(getText().getSpanStart(publishContentTextSpan), getText().getSpanEnd(publishContentTextSpan));
                    break;
                }
            }
        }
    }
    /**
     * 生成一个需要整体删除的Span
     * @param sps
     * @param unSpanText
     */
    private void makeSpan(Spannable sps, UnSpanText unSpanText) {
        PublishContentTextSpan what = new PublishContentTextSpan(unSpanText.returnText);
        PublishContentTextColorSpan circleWhat = new PublishContentTextColorSpan(ContextCompat.getColor(getContext(), R.color.colorAccent));
        PublishContentTextClickSpan circleClickWhat = new PublishContentTextClickSpan(getContext());
        int start = unSpanText.start;
        int end = unSpanText.end;
        sps.setSpan(what, start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        sps.setSpan(circleWhat, start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        sps.setSpan(circleClickWhat, start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
    }

    /**
     * 文字块
     */
    private class UnSpanText {
        int start;
        int end;
        String returnText;

        UnSpanText(int start, int end, String returnText) {
            this.start = start;
            this.end = end;
            this.returnText = returnText;
        }
    }
}

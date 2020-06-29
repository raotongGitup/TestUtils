package com.example.recyclerview;

import android.util.SparseArray;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/**
 * create at 2020/6/29
 * author raotong
 * Description : 通用的viewHolder封装
 */
public class CommonHolder extends RecyclerView.ViewHolder {

    /**
     * 减少子类findviewByid的次数
     */
    private SparseArray<View> mViews;

    public CommonHolder(@NonNull View itemView) {
        super(itemView);
        mViews = new SparseArray<>();
    }

    /*通过id获取view*/
    public <T extends View> T getView(int viewId) {
        View view = mViews.get(viewId);
        if (view == null) {
            view = itemView.findViewById(viewId);
            mViews.put(viewId, view);

        }
        return (T) view;

    }

    /*设置textview*/
    public CommonHolder setText(int viewid, CharSequence sequence) {
        TextView textView = getView(viewid);
        textView.setText(sequence);
        return this;
    }
    /*
     * 设置image的图片
     * */
    public CommonHolder setImageResource(int viewId, int resource) {
        ImageView imageView = getView(viewId);
        imageView.setImageResource(resource);

        return this;
    }
    /**
     * 隐藏布局
     */
    public CommonHolder setViewVisibility(int viewId, int visiblity) {
        View view = getView(viewId);
        view.setVisibility(visiblity);
        return this;
    }
    /**
     * 点击事件
     */
    public void setOnClickLister(View.OnClickListener lister) {
        itemView.setOnClickListener(lister);

    }
    /**
     * 长按点击事件
     */
    public void setLongOnClickLister(View.OnLongClickListener lister) {
        itemView.setOnLongClickListener(lister);
    }

}

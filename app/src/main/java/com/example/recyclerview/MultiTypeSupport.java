package com.example.recyclerview;

/**
 * create at 2020/6/29
 * author raotong
 * Description : 多布局支持接口
 */
public interface MultiTypeSupport<T> {
    // 根据不同的条目展示不同的布局
    public int getLayoutId(T item, int position);
}

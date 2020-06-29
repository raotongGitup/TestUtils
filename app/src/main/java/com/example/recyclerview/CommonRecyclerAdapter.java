package com.example.recyclerview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

/**
 * create at 2020/6/24
 * author raotong
 * Description : 通用的adapter的封装
 */
public abstract class CommonRecyclerAdapter<T> extends RecyclerView.Adapter<CommonHolder> {

    private Context mContext;
    private LayoutInflater inflater;
    private List<T> mDatas;
    /*
     * 布局传递过来
     * */
    private int mLayout;

    /*多布局的支持*/
    private MultiTypeSupport mMultiTypeSupport;

    public CommonRecyclerAdapter(Context mContext, List<T> mDatas, int mLayout) {
        this.mContext = mContext;
        this.inflater = inflater;
        this.mDatas = mDatas;
        this.mLayout = mLayout;
    }

    public CommonRecyclerAdapter(Context mContext, List<T> mDatas, int mLayout, MultiTypeSupport<T> mMultiTypeSupport) {
        this(mContext, mDatas, -1);
        this.mMultiTypeSupport = mMultiTypeSupport;
    }


    public void setmMultiTypeSupport(MultiTypeSupport mMultiTypeSupport) {
        this.mMultiTypeSupport = mMultiTypeSupport;
    }

    @NonNull
    @Override
    public CommonHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (mMultiTypeSupport != null) {
            mLayout = viewType;
        }
        View view = inflater.inflate(mLayout, parent, false);
        CommonHolder holder = new CommonHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull CommonHolder holder, int position) {



        convert(holder, mDatas.get(position));
    }


    @Override
    public int getItemViewType(int position) {
        if (mMultiTypeSupport != null) {
            return mMultiTypeSupport.getLayoutId(mDatas.get(position), position);
        }

        return super.getItemViewType(position);
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }
    /*
     * 利用抽象方法将adapter传递出去
     * */
    public abstract void convert(RecyclerView.ViewHolder holder, T item);



    /*
    * 设置条目的点击事件
//    * */
//    public OnItemClickListener mItemClickListener;
//    public OnLongClickListener mLongClickListener;
//
//    public void setOnItemClickListener(OnItemClickListener itemClickListener) {
//        this.mItemClickListener = itemClickListener;
//    }
//
//    public void setOnLongClickListener(OnLongClickListener longClickListener) {
//        this.mLongClickListener = longClickListener;
//    }




}

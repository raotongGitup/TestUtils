package com.example.layoutManager;

import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

/**
 * create at 2020/8/20
 * author raotong
 * Description : 实现TT语音的礼物面板
 */
public class TurntableLayoutManager extends RecyclerView.LayoutManager {

    private int mRadius;// 转盘的半径
    private int mEachAngle;  // 每个item之间的角度差

    private int mItemWidth;// item的宽
    private int mItemHeight; // item的高
    private int mCircleMidX;// 圆心的x作标
    private int mCircleMidY; // 圆心的y坐标
    private float mMovedAngle = 0;// 转盘移动的整体角度

    public TurntableLayoutManager(int mRadius, int mEachAngle) {
        this.mRadius = mRadius;
        this.mEachAngle = mEachAngle;
    }

    @Override
    public RecyclerView.LayoutParams generateDefaultLayoutParams() {
        return new RecyclerView.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    }

    @Override
    public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {
        initItemSize(recycler);
        // 2.获取圆心的坐标
        int screenMidX = getWidth() / 2;
        int screenHight = getHeight() / 2;
        mCircleMidX = screenMidX;
        mCircleMidY = screenHight + mRadius;
        //3.回收屏幕中的所有item
        detachAndScrapAttachedViews(recycler);
        //4.当item的角度值小于50时将其布局到屏幕内
        for (int i = 0; i < getChildCount(); i++) {
            if (Math.abs(i * mEachAngle) < 50) {
                layoutViewByIndex(recycler, i);
            }

        }


    }


    @Override
    public boolean canScrollHorizontally() {
        return true;
    }

    @Override
    public boolean canScrollVertically() {
        return false;
    }

    @Override
    public int scrollHorizontallyBy(int dx, RecyclerView.Recycler recycler, RecyclerView.State state) {
        float moveAngle = convertDxToAngle(dx);
        int actualDx = dx;
        if (mMovedAngle + moveAngle > getMaxScrollAngle()) {
            moveAngle = getMaxScrollAngle() - mMovedAngle;
            actualDx = convertAngleToDx(moveAngle);
        } else if (mMovedAngle + moveAngle < 0) {
            moveAngle = -mMovedAngle;
            actualDx = convertAngleToDx(moveAngle);
        }
        mMovedAngle += moveAngle;
        for (int i = 0; i < getChildCount(); i++) {
            View view = getChildAt(i);
            if (view != null) {
                int position = getPosition(view);
                float curAngle = position * mEachAngle + mMovedAngle;
                if (Math.abs(curAngle) >= 50) {
                    removeAndRecycleView(view, recycler);
                }
            }
        }
        // 回收当前屏幕上的所有ItemView
        detachAndScrapAttachedViews(recycler);
        for (int i = 0; i < getItemCount(); i++) {
            float curAngle = i * mEachAngle - mMovedAngle;
            if (Math.abs(curAngle) < 50) {
                layoutViewByIndex(recycler, i);
            }
        }
        return actualDx;
    }

    private int getMaxScrollAngle() {
        return (getItemCount() - 1) * mEachAngle;
    }

    private float convertDxToAngle(int dx) {
        return (float) (360 * dx / (2 * Math.PI * mRadius));
    }

    private int convertAngleToDx(float angle) {
        return (int) (2 * Math.PI * mRadius * angle / 360);
    }

    // 将数据添加到屏幕上
    private void layoutViewByIndex(RecyclerView.Recycler recycler, int index) {
        float curAngle = index * mEachAngle - mMovedAngle;
        int xToAdd = (int) (Math.sin(2 * Math.PI / 360 * curAngle) * mRadius);
        int yToMinus = (int) (Math.cos(2 * Math.PI / 360 * curAngle) * mRadius);
        int x = mCircleMidX + xToAdd;
        int y = mCircleMidY - yToMinus;

        View viewForPosition = recycler.getViewForPosition(index);
        addView(viewForPosition);
        measureChildWithMargins(viewForPosition, 0, 0);
        // 将item布局
        layoutDecorated(viewForPosition, x - mItemWidth / 2, y - mItemHeight / 2,
                x + mItemWidth / 2, y + mItemHeight / 2);
        // 调整Item自身的旋转角度
        viewForPosition.setRotation(curAngle);

    }


    // 获取item的大小
    private void initItemSize(RecyclerView.Recycler recycler) {
        View view = recycler.getViewForPosition(0);
        addView(view);
        measureChildWithMargins(view, 0, 0);
        mItemWidth = getDecoratedMeasuredWidth(view);
        mItemHeight = getDecoratedMeasuredHeight(view);
        removeAndRecycleView(view, recycler);
    }
}

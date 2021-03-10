package com.example.testutils.base.banner;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import java.util.ArrayList;
import java.util.List;

/**
 * banner自定义viewpage
 */
public class BannerViewPage extends ViewPager {
    public BannerAdapter adapter;
    private static final int MESSAGE_WHAT = BannerConfig.MESSAGE_WHAT;
    private int START_TIME = BannerConfig.START_TIME;

    public List<View> mConvertViews;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case MESSAGE_WHAT:
                    setCurrentItem(getCurrentItem() + 1);
                    handler.sendEmptyMessageDelayed(MESSAGE_WHAT, START_TIME);
                    break;
            }
        }
    };


    public BannerViewPage(@NonNull Context context) {
        this(context, null);
    }

    public BannerViewPage(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        mConvertViews = new ArrayList<>();

    }

    public void setAdapter(BannerAdapter adapter) {
        this.adapter = adapter;
        setAdapter(new BannerPagerAdapter());


    }

    /*播放时间*/
    public void setDelayTime(int delayTime) {
        START_TIME = delayTime;

    }

    /*开启播放*/
    public void setStartbanner() {

        handler.removeMessages(MESSAGE_WHAT); // 防止多条消息
        handler.sendEmptyMessageDelayed(MESSAGE_WHAT, START_TIME);


    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                handler.removeMessages(MESSAGE_WHAT);
                break;
            case MotionEvent.ACTION_UP:
                if(BannerConfig.mShuffling){
                    setStartbanner();
                }

                break;

        }

        return super.dispatchTouchEvent(ev);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        handler.removeMessages(MESSAGE_WHAT);
    }

    public  class BannerPagerAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return Integer.MAX_VALUE;
        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
            return view == object;
        }

        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, int position) {
            View view = adapter.getView(position % adapter.getContent(), getConverViews());


            container.addView(view);
            return view;
        }


        @Override
        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
            container.removeView((View) object);
            mConvertViews.add((View) object);
        }

        /*
         *   界面复用
         * */
        private View getConverViews() {
            for (int i = 0; i < mConvertViews.size(); i++) {
                if (mConvertViews.get(i).getParent() == null) {
                    return mConvertViews.get(i);
                }

            }
            return null;
        }
    }
}

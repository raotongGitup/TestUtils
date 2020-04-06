package com.example.testutils.base.banner;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import androidx.viewpager.widget.ViewPager;

import com.example.testutils.R;

import java.lang.reflect.Field;

/**
 * 供外部使用的bannerView
 */
public class BannerView extends RelativeLayout {
    private Context mContent;
    private View view;
    private BannerViewPage viewPage;
    private LinearLayout bannerContainer;
    private BannerAdapter adapter;
    private int IndicatorPosition;
    private boolean isAccording = true;
    private Drawable checkIndication;
    private Drawable nokIndication;
    private int mCurrentPosition;
    private int mDosize;


    public BannerView(Context context) {
        this(context, null);
    }

    public BannerView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BannerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContent = context;
        view = LayoutInflater.from(context).inflate(R.layout.bnner_view, this);
        initView();
        inttType(attrs);

    }

    private void inttType(AttributeSet attrs) {
        TypedArray array = mContent.obtainStyledAttributes(attrs, R.styleable.BannerView);
        IndicatorPosition = array.getInt(R.styleable.BannerView_doGravity, 0);
        mDosize = array.getInt(R.styleable.BannerView_dotSize, 6);
        checkIndication = array.getDrawable(R.styleable.BannerView_dotIndicationCheck);
        nokIndication = array.getDrawable(R.styleable.BannerView_dotIndicationNo);
        if (checkIndication == null || nokIndication == null) {
            checkIndication = new ColorDrawable(Color.RED);
            nokIndication = new ColorDrawable(Color.WHITE);
        }

        array.recycle();


    }

    /**
     * 初始圆点指示器
     */
    private void initDotIndicator() {
        int count = adapter.getContent();
        for (int i = 0; i < count; i++) {

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(dip2px(mDosize), dip2px(mDosize));
            params.leftMargin = params.rightMargin = dip2px(2);
            DotIndicatorView dotIndicatorView = new DotIndicatorView(mContent);
            dotIndicatorView.setLayoutParams(params);
            if (i == 0) {
                dotIndicatorView.setDrawable(checkIndication);
            } else {
                dotIndicatorView.setDrawable(nokIndication);
            }
            bannerContainer.addView(dotIndicatorView);

        }


    }

    private void initView() {
        viewPage = ((BannerViewPage) findViewById(R.id.banner_page));
        bannerContainer = ((LinearLayout) findViewById(R.id.dot_container));
        initViewPagerScroll();
        switch (IndicatorPosition) {
            case -1:
                bannerContainer.setGravity(Gravity.LEFT);
                break;
            case 1:
                bannerContainer.setGravity(Gravity.RIGHT);

                break;
            default:
                bannerContainer.setGravity(Gravity.CENTER);
                break;
        }
    }

    public void setAdapter(BannerAdapter adapter) {
        this.adapter = adapter;
        viewPage.setAdapter(adapter);
        viewPage.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (isAccording) {
                    pageSelect(position);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        if (isAccording) {
            initDotIndicator();
        }

    }

    /**
     * 设置选中的状态
     */
    private void pageSelect(int position) {
        DotIndicatorView oldIndicatorView = (DotIndicatorView)
                bannerContainer.getChildAt(mCurrentPosition);
        oldIndicatorView.setDrawable(nokIndication);
        mCurrentPosition = position % adapter.getContent();
        DotIndicatorView currentIndicatorView = (DotIndicatorView)
                bannerContainer.getChildAt(mCurrentPosition);
        currentIndicatorView.setDrawable(checkIndication);


    }

    /**
     * 设置是否显示指示器
     */
    public void setIsAccording(boolean isAccording) {
        this.isAccording = isAccording;

    }

    /**
     * 设置指示器选中和没选中颜色（指示器为默认圆形）
     */
    public void setIndicationColor(String check, String noCheck) {
        checkIndication = new ColorDrawable(Color.parseColor(check));
        nokIndication = new ColorDrawable(Color.parseColor(noCheck));

    }

    /**
     * 开始播放
     */
    public void setStartbanner() {
        viewPage.setStartbanner();
    }

    /**
     * 设置指示器显示位置，0 居中，-1左侧，1 右侧
     */
    public void setIndicatorGravity(int gravity) {
        this.IndicatorPosition = gravity;

    }

    /**
     * 圆点大小
     */
    public void setmDosize(int mDosize) {
        this.mDosize = mDosize;

    }

    /**
     * 设置viewpage切切换时间
     */
    private void initViewPagerScroll() {
        try {
            Field mField = ViewPager.class.getDeclaredField("mScroller");
            mField.setAccessible(true);
            BannerScroller mScroller = new BannerScroller(viewPage.getContext(), new AccelerateInterpolator());
            mField.set(viewPage, mScroller);
        } catch (Exception e) {
            Log.e("viewpage切换速率", e.getMessage());
        }
    }


    /**
     * 5.把dip转成px
     */
    private int dip2px(int dip) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                dip, getResources().getDisplayMetrics());
    }
}

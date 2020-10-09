package com.example.view;

import android.animation.ArgbEvaluator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RoundRectShape;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.example.testutils.R;
import com.example.utils.SizeUtils;

/**
 * create at 2020/9/7
 * author raotong
 * Description : 实现自定义view控件选择时得颜色渐变的过程
 */
public class GenderSwitchView extends View {
    private Context mContext;
    private ShapeDrawable backgroundDrawable;
    private ShapeDrawable genderDrawable;
    private float mProgress;
    private int mTouchSlop;

    private int height;
    private int width;

    private int mClickTimeout;

    private Paint selectTextPaint;
    private Paint defaultTextPaint;

    private int grayText; // 没有选中的时候文字的颜色

    private ValueAnimator mProgressAnimator;

    private int girlStartColor;
    private int girlEndColor;

    private int boyStartColor;
    private int boyEndColor;

    private ArgbEvaluator argbEvaluator;
    private LinearGradient linearGradient;

    private int boundsWidth;
    private int bundsX;
    private Rect bounds;

    private int mAnimationDuration=500;// 动画的滑动时间

    private Bitmap girlSign;
    private Bitmap boySign;
    private int drawBitmapY;
    private int drawBitmapX;
    private Paint bitmapPaint;
    private float drawTextX;
    private float drawTextY;
    private Bitmap whiteGirlSign;
    private Bitmap whiteBoySign;


    public GenderSwitchView(Context context) {
        this(context, null);
    }

    public GenderSwitchView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public GenderSwitchView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
        initView();
    }

    private void initView() {
        int testSize = SizeUtils.sp2px(16);
        //这里是将宽高根据ui 设计图计算写死
        height = SizeUtils.dp2px(45);
        width = SizeUtils.dp2px(200);
        //圆角角度
        int radiis = SizeUtils.dp2px(80);
        //获取系统识别最小的滑动距离
        mTouchSlop = ViewConfiguration.get(getContext()).getScaledTouchSlop();
        //获取系统触发点击事件的时长
        mClickTimeout = ViewConfiguration.getPressedStateDuration() + ViewConfiguration.getTapTimeout();
        grayText = ContextCompat.getColor(mContext, R.color.col_aaabb3);
        bitmapPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

        whiteGirlSign = BitmapFactory.decodeResource(mContext.getResources(), R.mipmap.sl_my_male);
        whiteBoySign = BitmapFactory.decodeResource(mContext.getResources(), R.mipmap.xiaojindou);

        girlSign = whiteGirlSign;
        boySign = whiteBoySign;

        selectTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        selectTextPaint.setTextAlign(Paint.Align.CENTER);
        selectTextPaint.setTextSize(testSize);
        selectTextPaint.setColor(Color.WHITE);

        defaultTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        defaultTextPaint.setTextAlign(Paint.Align.CENTER);
        defaultTextPaint.setTextSize(testSize);
        defaultTextPaint.setColor(grayText);

        mProgressAnimator = new ValueAnimator();

        float[] outerRadii = {radiis, radiis, radiis, radiis, radiis, radiis, radiis, radiis};//外矩形 左上、右上、右下、左下的圆角半径
        RectF inset = new RectF(0, 0, 0, 0);
        float[] innerRadii = {0, 0, 0, 0, 0, 0, 0, 0};//内矩形 圆角半径
        RoundRectShape roundRectShape = new RoundRectShape(outerRadii, inset, innerRadii);
        backgroundDrawable = new ShapeDrawable(roundRectShape);
        int back_color = ContextCompat.getColor(mContext, R.color.col_f3f3f3);
        backgroundDrawable.getPaint().setColor(back_color);
        backgroundDrawable.setBounds(0, 0, width, height);

        girlStartColor = ContextCompat.getColor(mContext, R.color.col_ff719e);
        girlEndColor = ContextCompat.getColor(mContext, R.color.col_ffae9b);

        boyStartColor = ContextCompat.getColor(mContext, R.color.col_55a8ff);
        boyEndColor = ContextCompat.getColor(mContext, R.color.col_8998ff);

        //渐变色计算类
        argbEvaluator = new ArgbEvaluator();
        RoundRectShape shape = new RoundRectShape(outerRadii, inset, innerRadii);
        linearGradient = new LinearGradient(0, 0, boundsWidth, height, girlStartColor, girlEndColor, Shader.TileMode.REPEAT);

        genderDrawable = new ShapeDrawable(shape);
        genderDrawable.getPaint().setShader(linearGradient);
        genderDrawable.getPaint().setStyle(Paint.Style.FILL);
        boundsWidth = width / 2;
        bundsX = (int) (mProgress * boundsWidth);
        bounds = new Rect(bundsX, 0, boundsWidth + bundsX, height);
        genderDrawable.setBounds(bounds);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(width, height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        backgroundDrawable.draw(canvas);//先绘制背景Drawable
        genderDrawable.draw(canvas);//再绘制上面一层用于可滑动的Drawable
        canvas.drawBitmap(girlSign, drawBitmapX, drawBitmapY, bitmapPaint);
        canvas.drawBitmap(boySign, width / 2 + drawBitmapX, drawBitmapY, bitmapPaint);
        canvas.drawText("女士", drawTextX, drawTextY, selectTextPaint);
        canvas.drawText("男士", width / 2 + drawTextX, drawTextY, defaultTextPaint);
    }

    float mStartX;
    float mStartY;
    float mLastX;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();
        float deltaX = event.getX() - mStartX;
        float deltaY = event.getY() - mStartY;
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                mStartX = event.getX();
                mStartY = event.getY();
                float x = event.getX();
                mLastX = mStartX;
                setPressed(true);

                break;
            case MotionEvent.ACTION_MOVE:
                float xs = event.getX();
                //计算滑动的比例 boundsWidth为整个宽度的一半
                setProcess(getProgress() + (xs - mLastX) / boundsWidth);
                //这里比较x轴方向的滑动 和y轴方向的滑动 如果y轴大于x轴方向的滑动 事件就不在往下传递
                if ((Math.abs(deltaX) > mTouchSlop / 2 || Math.abs(deltaY) > mTouchSlop / 2)) {
                    if (Math.abs(deltaY) > Math.abs(deltaX)) {
                        return false;
                    }
                }
                mLastX = xs;
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                setPressed(false);
                //计算从手指触摸到手指抬起时的时间
                float time = event.getEventTime() - event.getDownTime();
                //如果x轴和y轴滑动距离小于系统所能识别的最小距离 切从手指按下到抬起时间 小于系统默认的点击事件触发的时间  整个行为将被视为触发点击事件
                if (Math.abs(deltaX) < mTouchSlop && Math.abs(deltaY) < mTouchSlop && time < mClickTimeout) {
                    //获取事件触发的x轴区域 主要用于区分是左边还是右边
                    float clickX = event.getX();

                    //如果是在左边
                    if (clickX > boundsWidth) {
                        if (mProgress == 1.0f) {
                            return false;
                        } else {
                            animateToState(true);
                        }
                    } else {
                        if (mProgress == 0.0f) {
                            return false;
                        } else {
                            animateToState(false);
                        }
                    }
                    return false;
                } else {
                    boolean nextStatus = getProgress() > 0.5f;
                    animateToState(nextStatus);
                }

                break;

        }


        return true;
    }

    public void setProcess(float progress) {
        Log.e("GenderSwitchView,进度", progress + "");
        float tp = progress;
        if (tp > 1) {
            tp = 1;
        } else if (tp < 0) {
            tp = 0;
        }
        updatePaintStyle(tp);
        this.mProgress = tp;
        bundsX = (int) (mProgress * boundsWidth);
        bounds.left = bundsX;
        bounds.right = boundsWidth + bundsX;
        genderDrawable.setBounds(bounds);
        invalidate();
    }

    // 通过滑块的长度来计算颜色的渐变色
    public void updatePaintStyle(float tp) {
        int startColor = (int) (argbEvaluator.evaluate(tp, girlStartColor, boyStartColor));
        int endColor = (int) (argbEvaluator.evaluate(tp, girlEndColor, boyEndColor));
        LinearGradient linearGradient = new LinearGradient(0, 0, boundsWidth, height, startColor, endColor, Shader.TileMode.REPEAT);
        //将计算好的 颜色范围 重新设置到Drawable
        genderDrawable.getPaint().setShader(linearGradient);

    }

    public float getProgress() {
        return mProgress;
    }

    public void animateToState(boolean checked) {
        float progress = mProgress;
        if (mProgressAnimator == null) {
            return;
        }
        if (mProgressAnimator.isRunning()) {
            mProgressAnimator.cancel();
            mProgressAnimator.removeAllUpdateListeners();
        }
        mProgressAnimator.setDuration(mAnimationDuration);
        if (checked) {
            //右边
            mProgressAnimator.setFloatValues(progress, 1f);
        } else {
            //左边
            mProgressAnimator.setFloatValues(progress, 0.0f);
        }
        mProgressAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mProgress = (float) animation.getAnimatedValue();
                //通过ValueAnimator 进度更新 Drawable 渐变色范围
                updatePaintStyle(mProgress);
                bundsX = (int) (mProgress * boundsWidth);
                bounds.left = bundsX;
                bounds.right = boundsWidth + bundsX;
                //更新性别选择Drawable的绘制范围
                genderDrawable.setBounds(bounds);
                //绘制
                postInvalidate();

            }
        });
        mProgressAnimator.start();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        //计算图片绘制的 x,y
        drawBitmapX = SizeUtils.dp2px(22);
        int textMargin = SizeUtils.dp2px(5);
        drawBitmapY = (height - girlSign.getHeight()) / 2;

        String mText = "男士";
        Rect bounds = new Rect();
        //测量文字的宽度
        selectTextPaint.getTextBounds(mText, 0, mText.length(), bounds);
        //获取文字的高度
        int textHeight = bounds.height();
        //计算文字绘制的 x,y
        drawTextX = drawBitmapX + girlSign.getWidth() + textMargin + bounds.width() / 2;
        drawTextY = height / 2 + textHeight / 2;


    }
}

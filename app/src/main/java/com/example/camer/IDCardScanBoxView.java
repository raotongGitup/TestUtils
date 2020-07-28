package com.example.camer;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.example.testutils.R;

/**
 * create at 2020/7/28
 * author raotong
 * Description : 自定义身份证样式view
 */
public class IDCardScanBoxView extends View {


    private Context context;

    private int frameWidth;   //透明区域宽度
    private int frameHeight;  //透明区域高度
    private int frameTop;     //透明区域距顶部距离
    private int frameLeft;    //透明区域距左边距离

    private Paint paint;  //画笔
    private Bitmap bitmap;

    public IDCardScanBoxView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);

        bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.error);
        frameWidth = bitmap.getWidth();//Util.dp2px(context, 301);
        frameHeight = bitmap.getHeight(); //Util.dp2px(context, 192);
//        frameWidth = 301;//Util.dp2px(context, 301);
//        frameHeight =192; //Util.dp2px(context, 192);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        //获取屏幕的宽和高
        int width = canvas.getWidth();
        int height = canvas.getHeight();

        frameLeft = ((width - frameWidth) / 2);
        frameTop = 150;

        paint.setColor(Color.RED);
        //上阴影
        canvas.drawRect(0, 0, width, frameTop, paint);
        //下阴影
        canvas.drawRect(0, frameTop + frameHeight, width, height, paint);
        //左阴影
        canvas.drawRect(0, frameTop, frameLeft, frameTop + frameHeight, paint);
        //右阴影
        canvas.drawRect(frameLeft + frameWidth, frameTop, width, frameTop + frameHeight, paint);
        //中间透明拍照框
      //  canvas.drawBitmap(bitmap, frameLeft, frameTop, paint);

        Log.e("IDCardScanBoxView", "高度宽度========>" + "height:" + height + ",width:" + width + ",frameTop:" + frameTop + ",frameLeft:" + frameLeft + ",frameHeight:" + frameHeight + ",frameWidth:" + frameWidth);
    }


    public int getFrameWidth() {
        return frameWidth;
    }

    public int getFrameHeight() {
        return frameHeight;
    }

}

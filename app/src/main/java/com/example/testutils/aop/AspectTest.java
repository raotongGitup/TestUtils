package com.example.testutils.aop;

import android.util.Log;

import com.example.testutils.utils.NoDoubleClickUtils;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;

/**
 * create at 2020/6/2
 * author raotong
 * Description : 处理切点类
 */
@Aspect
public class AspectTest {
    private String TAG = AspectTest.class.getSimpleName();
    private boolean isDoubleClick = false; // 用于判断是否重复点击

    @Before("execution(@com.example.testutils.aop.DoubleClick  * *(..))")
    public void beforeEnableDoubleClcik(JoinPoint joinPoint) throws Throwable {
        //用于处理切点所在的类
        Log.e(TAG, "beforeEnableDoubleClcik");
        isDoubleClick = true;

    }

    @Around("execution(* android.view.View.OnClickListener.onClick(..))")
    public void onClickLitener(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        // 用于处理切点标记的方法，和类
        Log.e(TAG, "onClickLitener");
        if (isDoubleClick || !NoDoubleClickUtils.isDoubleClick()) {
            proceedingJoinPoint.proceed();
            isDoubleClick = false;
        }

    }


}

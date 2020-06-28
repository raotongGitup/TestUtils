package com.example.testutils.aop;

import android.util.Log;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * author  raotong
 * Data2020/6/29
 **/
@Aspect
public class BehaviorAspect {
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd  HH:mm:ss");
    private String TAG = "jason";

    // 切点 (切带有 BehaviorTrace 当前注解的地方)
    @Pointcut("execution(@com.example.testutils.aop.BehaviorTrace * *(..))")
    public void annorBehavior() {

    }

    // 切完以后如何处理
    @Around("annorBehavior()")
    public Object waveJoinPoint(ProceedingJoinPoint joinPoint) throws Throwable {
        //  方法执行前
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        BehaviorTrace anno = methodSignature.getMethod().getAnnotation(BehaviorTrace.class);
        String funName = anno.value();
        Log.e(TAG, funName + "使用时间 " + sdf.format(new Date()));
        long begin = System.currentTimeMillis();
        // 执行方法
        Object object = joinPoint.proceed();
        // 方法执行后
        long duration = System.currentTimeMillis() - begin;

        Log.e(TAG, funName + "消耗时间" + duration);


        return object;
    }

}

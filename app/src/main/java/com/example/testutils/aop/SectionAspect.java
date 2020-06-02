package com.example.testutils.aop;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.view.View;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;

/**
 * create at 2020/6/2
 * author raotong
 * Description : 切点处理类
 */
@Aspect
public class SectionAspect {
    private String TAG = SectionAspect.class.getSimpleName();

    /**
     * 找到切点处理切点类
     */
    @Pointcut("execution(@com.example.testutils.aop.CheckNet * *(..))")
    public void checkNetBehavior() {

    }

    /**
     * 处理切面的方法
     */

    @Around("checkNetBehavior()")
    public Object checkNet(ProceedingJoinPoint joinPoint) throws Throwable {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        CheckNet checkNet = signature.getMethod().getAnnotation(CheckNet.class);
        if (checkNet != null) {
            Object object = joinPoint.getThis();
            Context context = getContext(object);
            if (context != null) {
                if (!isNetworkAvailable(context)) {
                    Toast.makeText(context, "请检查网络是否打开", Toast.LENGTH_SHORT).show();

                    return null;
                }

            }

        }

        return joinPoint.proceed();

    }

    /**
     * 通过对象回去上下文
     */
    private Context getContext(Object object) {
        if (object instanceof Activity) {
            return (Activity) object;
        } else if (object instanceof Fragment) {
            Fragment fragment = (Fragment) object;
            return fragment.getActivity();
        } else if (object instanceof View) {
            View view = (View) object;
            return view.getContext();
        }
        return null;

    }

    /**
     * 检测网络
     */
    private static boolean isNetworkAvailable(Context context) {
        // 获取手机所有连接管理对象（包括对wi-fi,net等连接的管理）
        ConnectivityManager connectivityManager = (ConnectivityManager)
                context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager != null) {
            // 获取NetworkInfo对象
            NetworkInfo[] networkInfo = connectivityManager.getAllNetworkInfo();

            if (networkInfo != null && networkInfo.length > 0) {
                for (int i = 0; i < networkInfo.length; i++) {
                    // 判断当前网络状态是否为连接状态
                    if (networkInfo[i].getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

}

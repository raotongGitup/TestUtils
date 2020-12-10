package com.example.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import androidx.annotation.NonNull;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * create at 2020/12/10
 * author raotong
 * Description : 解决安卓中90%的崩溃
 */
public class CrashHandler implements Thread.UncaughtExceptionHandler {

    private static CrashHandler INSTANCE = new CrashHandler();

    private Context mContext = null;
    /**
     * log tag； 日志标签
     **/
    private final String TAG = "error_log";

    /**
     * log file name； 日志文件的文件名
     **/
    private final String mFileName = "error_log.txt";

    /***/
    private ExceptionHandler mExceptionHandler = null;

    Thread.UncaughtExceptionHandler mDefaultExceptionHandler = null;

    public static CrashHandler getInstance() {
        return INSTANCE;
    }

    @Override
    public void uncaughtException(@NonNull Thread thread, @NonNull Throwable ex) {
        if (!handleException(ex) && mDefaultExceptionHandler != null) {
            mDefaultExceptionHandler.uncaughtException(thread, ex);
        }

    }

    /**
     * @param context
     * @functions：init applicatin crash handler; 初始化应用异常处理
     */
    public void init(Context context) {
        this.mContext = context;
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        Looper.loop();
                    } catch (Exception e) {
                        if (e instanceof ExitMessage) {
                            break;
                        }
                        handleException(e);
                    }
                }
            }
        });

        mDefaultExceptionHandler = Thread.getDefaultUncaughtExceptionHandler(); // get default handler
        Thread.setDefaultUncaughtExceptionHandler(this);
    }

    public void setErrorHandler(ExceptionHandler handler) {
        mExceptionHandler = handler;
    }

    /**
     * @functin: exit main crash; 退出主线程异常处理
     */
    public void exit() {
        Thread.setDefaultUncaughtExceptionHandler(this);

        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                throw new ExitMessage();
            }
        });
    }

    private boolean handleException(Throwable e) {
        if (e == null || mContext == null) {
            return false;
        }
        if (mExceptionHandler != null) {
            mExceptionHandler.onError(e);
        }
        collectLog(e);
        return true;
    }

    private String collectLog(Throwable e) {
        StackTraceElement[] elements = e.getStackTrace();
        StringBuilder sb = new StringBuilder();

        getPackagetInfo(sb);
        for (StackTraceElement element : elements) {
            sb.append(element.toString() + "\n");
        }

        Log.e(TAG, sb.toString());
        uploadLog(saveLog(sb.toString()));
        return sb.toString();
    }

    public void getPackagetInfo(StringBuilder sb) {
        PackageInfo info = null;
        try {
            info = mContext.getPackageManager().getPackageInfo(
                    mContext.getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace(System.err);
        }
        if (info == null)
            info = new PackageInfo();

        SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyy--HH:mm:ss");
        Date date = new Date(System.currentTimeMillis());
        String dateStr = format.format(date);
        sb.append("-------------------------------------------------------------------\n");
        sb.append("Version: " + info.versionName + "(" + info.versionCode + ")\n");
        sb.append("Android: " + Build.VERSION.RELEASE + "(" + Build.MODEL + ")\n");
        sb.append("Exception: " + info.toString() + "\n");
        sb.append("Time: " + dateStr + "\n");
    }

    /**
     * @param log
     * @return
     */
    private File saveLog(String log) {
        File file = new File(mContext.getExternalCacheDir(), mFileName);
        try {
            if (!file.exists()) {
                file.getParentFile().mkdirs();
                file.createNewFile();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        try {
            FileInputStream inputStream = new FileInputStream(file);
            byte[] data = new byte[inputStream.available()];
            inputStream.read(data);
            log = new String(data) + "\n" + log;


            FileOutputStream fileWirter = new FileOutputStream(file);
            fileWirter.write(log.getBytes());
            fileWirter.flush();
            fileWirter.close();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return file;
    }

    public void uploadLog(File file) {
        // 将错误日志上传到服务器上


    }

    public interface ExceptionHandler {
        void onError(Throwable e);
    }

    private class ExitMessage extends RuntimeException {
        public ExitMessage() {
            super("daemon had been exit");
        }
    }

}

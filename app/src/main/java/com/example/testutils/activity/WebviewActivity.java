package com.example.testutils.activity;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.FrameLayout;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.example.testutils.R;

public class WebviewActivity extends AppCompatActivity {

    private FrameLayout fragmentlayout;

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);
        fragmentlayout = ((FrameLayout) findViewById(R.id.fragment_layout));

        initWebview();
    }

    WebView mWebView;

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    private void initWebview() {
        mWebView = new WebView(this);

        if (mWebView != null) {
            mWebView.setWebChromeClient(null);
            mWebView.setWebViewClient(null);
            mWebView.getSettings().setJavaScriptEnabled(false);
            mWebView.clearCache(true);//清除缓存
        }

        final WebSettings mWebSettings = mWebView.getSettings();
        mWebView.setBackgroundColor(0);
        mWebSettings.setJavaScriptEnabled(true);
        mWebView.setOverScrollMode(View.OVER_SCROLL_NEVER);
        mWebSettings.setDefaultTextEncodingName("utf-8");
        mWebSettings.setUseWideViewPort(true);
        mWebSettings.setLoadWithOverviewMode(true);
        mWebSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);
        mWebSettings.setDomStorageEnabled(true);
        mWebSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        mWebSettings.setAllowUniversalAccessFromFileURLs(true);
        mWebSettings.setAppCacheEnabled(true);
        mWebSettings.setDomStorageEnabled(true);
        mWebView.getSettings().setBlockNetworkImage(false);
        mWebView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                view.requestFocus();
                super.onProgressChanged(view, newProgress);

            }
        });
        mWebView.requestFocus(View.FOCUS_DOWN);
        mWebView.setEnabled(true);
        mWebView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        v.setFocusable(false);
                        break;
                    case MotionEvent.ACTION_UP:
                        v.setFocusable(true);

                        v.setFocusableInTouchMode(true);

                        v.requestFocus();

                        v.requestFocusFromTouch();

                        Log.e("GameRoomActivity", "onTouch: " + event.getAction() + "dd" + v.hasFocus());

                        break;
                }
                return false;

            }
        });
        mWebView.loadUrl("file:///android_asset/index.html");

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            mWebSettings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }

        mWebSettings.setPluginState(WebSettings.PluginState.ON);
        mWebSettings.setSupportZoom(true); // 支持缩放
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            mWebSettings.setAllowFileAccessFromFileURLs(true);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            mWebSettings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }
//        mWebView.setWebViewClient(new WebViewClient() {
//            @Override
//            public boolean shouldOverrideUrlLoading(WebView view, String url) {
//                view.loadUrl(url);
//                return true;
//            }
//        });


        //  mWebView.addJavascriptInterface(new AndroidAndJSInterface(), "keke");


//        mWebView.setWebChromeClient(new WebChromeClient() {
//            @Override
//            public void onProgressChanged(WebView view, int newProgress) {
////                if (newProgress >= 100) {
////                    loadingll.setVisibility(View.GONE);
////                    animationDrawable.stop();
////                } else {
////                    loadingll.setVisibility(View.VISIBLE);
////
////                }
//            }
//
//            @Override
//            public void onReceivedTitle(WebView view, String title) {
//                super.onReceivedTitle(view, title);
//
//            }
//        });
//        mWebView.setWebViewClient(new WebViewClient() {
//            @Override
//            public boolean shouldOverrideUrlLoading(WebView view, String url) {
//
//                return super.shouldOverrideUrlLoading(view, url);
//            }
//
//
//            @Override
//            public WebResourceResponse shouldInterceptRequest(WebView view, String url) {
//                if (url.startsWith("http") || url.startsWith("https")) {
//
//                    return super.shouldInterceptRequest(view, url);
//                } else {
//                    if (url.indexOf(".qq.com") > -1) {
//                        Intent in = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
//                        startActivity(in);
//                        return null;
//                    } else {
//                        return super.shouldInterceptRequest(view, url);
//                    }
//                }
//            }
//
//            @Override
//            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
//                super.onReceivedError(view, errorCode, description, failingUrl);
////                mNavTitleView.setText("精彩文章");
//            }
//
//            @Override
//            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
//                super.onReceivedSslError(view, handler, error);
////                UtilsLog.e(TAG, "ssl error = " + error.getPrimaryError());
//                handler.proceed();
//            }
//
//            @Override
//            public void onPageStarted(WebView view, String url, Bitmap favicon) {
//                super.onPageStarted(view, url, favicon);
//
//            }
//
//            @Override
//            public void onPageFinished(WebView view, String url) {
//                super.onPageFinished(view, url);
////                UtilsLog.i(TAG, "文章详情加载完成了 - url = " + url);
//            }
//        });
        fragmentlayout.addView(mWebView);

    }
}

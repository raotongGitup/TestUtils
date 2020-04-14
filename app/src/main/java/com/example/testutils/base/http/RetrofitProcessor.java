package com.example.testutils.base.http;

import android.content.Context;

import com.example.testutils.utils.NetUtils;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.CacheControl;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;


public class RetrofitProcessor implements IHttpProcessor {
    private Context context;
    private static final int READ_TIME_OUT = 10 * 1000;

    private static final int CONNECT_TIME_OUT = 10 * 1000;
    private boolean isPostField; // 判断post请求时用json类型，还是filed


    public RetrofitProcessor(Context context) {
        this.context = context;
    }

    public RetrofitProcessor(Context context, boolean isPostField) {
        this.context = context;
        this.isPostField = isPostField;
    }

    @Override
    public void post(String url, Map<String, Object> map, final HttpCallback callback) {
        if (isPostField) {
            apiRetrofit().getPost(url, getPOSTbody(map))
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<ResponseBody>() {
                        @Override
                        public void onSubscribe(Disposable d) {

                        }

                        @Override
                        public void onNext(ResponseBody responseBody) {
                            try {
                                String str = responseBody.string();
                                callback.onSuccent(str);

                            } catch (IOException e) {
                                e.printStackTrace();

                            }

                        }

                        @Override
                        public void onError(Throwable e) {
                            callback.onError(e.getMessage());

                        }

                        @Override
                        public void onComplete() {

                        }
                    });
        } else {
            apiRetrofit().postFielData(url, map)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<ResponseBody>() {
                        @Override
                        public void onSubscribe(Disposable d) {

                        }

                        @Override
                        public void onNext(ResponseBody responseBody) {
                            try {
                                String str = responseBody.string();
                                callback.onSuccent(str);

                            } catch (IOException e) {
                                e.printStackTrace();

                            }

                        }

                        @Override
                        public void onError(Throwable e) {
                            callback.onError(e.getMessage());

                        }

                        @Override
                        public void onComplete() {

                        }
                    });


        }


    }

    @Override
    public void get(String url, Map<String, Object> map, final HttpCallback callback) {
        apiRetrofit().getData(url, map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ResponseBody>() {
                    @Override
                    public void onSubscribe(Disposable d) {


                    }

                    @Override
                    public void onNext(ResponseBody responseBody) {
                        try {
                            String str = responseBody.string();
                            callback.onSuccent(str);

                        } catch (IOException e) {
                            e.printStackTrace();

                        }

                    }

                    @Override
                    public void onError(Throwable e) {
                        callback.onError(e.getMessage());

                    }

                    @Override
                    public void onComplete() {

                    }
                });


    }

    /**
     * retrofit的参数封装
     */
    public HttpInterface apiRetrofit() {
        // 请求的网络的拦截
        Interceptor mRewriteCacheControlInterceptor = new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request = chain.request();
                if (!NetUtils.hasNetwork(context)) {
                    request = request.newBuilder()
                            .cacheControl(CacheControl.FORCE_CACHE)
                            .build();

                }
                Response originalResponse = chain.proceed(request);
                if (NetUtils.hasNetwork(context)) {
                    //有网的时候读接口上的@Headers里的配置，你可以在这里进行统一的设置
                    String cacheControl = request.cacheControl().toString();
                    return originalResponse.newBuilder()
                            .header("Cache-Control", cacheControl)
                            .removeHeader("Pragma")
                            .build();
                } else {
                    return originalResponse.newBuilder()
                            .header("Cache-Control", "public, only-if-cached, max-stale=2419200")
                            .removeHeader("Pragma")
                            .build();
                }
            }
        };
        // 拦截添加请求头
        Interceptor headerInterceptor = new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request build = chain.request().newBuilder()
//                        .addHeader("DEVICE_INFO", DEVICE_INFO + "&" + "nowTime=" + simpleDateFormat.format(new Date()))
//                        .addHeader("User-Agent", DEFAULT_USERAGENT)
                        .build();
                return chain.proceed(build);

            }
        };
        // 拦截添加统一参数
        Interceptor requestParameter = new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                //偷天换日
                Request oldRequest = chain.request();

                //拿到拥有以前的request里的url的那些信息的builder
                HttpUrl.Builder builder = oldRequest
                        .url()
                        .newBuilder();

                //得到新的url（已经追加好了参数）
                HttpUrl newUrl = builder
//                        .addQueryParameter("deviceId", "12345")
//                        .addQueryParameter("token", "i_am_token")
//                        .addQueryParameter("appVersion", "1.0.0-beta")
                        .build();

                //利用新的Url，构建新的request，并发送给服务器
                Request newRequest = oldRequest
                        .newBuilder()
                        .url(newUrl)
                        .build();


                return chain.proceed(newRequest);
            }
        };

        OkHttpClient.Builder builder = new OkHttpClient.Builder()
                .addInterceptor(mRewriteCacheControlInterceptor)
                .addNetworkInterceptor(mRewriteCacheControlInterceptor)
                .readTimeout(READ_TIME_OUT, TimeUnit.MILLISECONDS)
                .connectTimeout(CONNECT_TIME_OUT, TimeUnit.MILLISECONDS);
        OkHttpClient okHttpClient = builder
                .addInterceptor(headerInterceptor) // 设置拦截器监听
                .addInterceptor(requestParameter)
                .build();
        Retrofit retrofit = new Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl(HttpUrlContent.BaseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        return retrofit.create(HttpInterface.class);
    }

    public static RequestBody getPOSTbody(Map<String, Object> map) {
        JSONObject jsonObject = new JSONObject(map);
        return RequestBody.create(MediaType.parse("application/x-www-form-urlencoded"),
                toBiao(map));
    }


    public static String toBiao(Map<String, Object> map) {
        return new Gson().toJson(map);
    }
}

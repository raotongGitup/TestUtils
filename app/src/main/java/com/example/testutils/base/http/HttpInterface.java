package com.example.testutils.base.http;


import java.util.Map;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.http.Body;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.QueryMap;
import retrofit2.http.Url;

/**
 * retrofit的网络请求
 */
public interface HttpInterface {

    @GET()
    Observable<ResponseBody> getData(@Url String url, @QueryMap Map<String, Object> map);

    @POST
    Observable<ResponseBody> getPost(@Url String url, @Body RequestBody Body);

    @FormUrlEncoded
    @POST
    Observable<ResponseBody> postFielData(@Url String url, @FieldMap Map<String, Object> map);

}

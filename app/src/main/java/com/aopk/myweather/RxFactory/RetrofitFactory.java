package com.aopk.myweather.RxFactory;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.simplexml.SimpleXmlConverterFactory;

/**
 * Created by Administrator on 2017/10/25.
 */

public class RetrofitFactory {
    private static final int TIME_OUT = 10;
    private static final String BASE_URL = "http://wthrcdn.etouch.cn/";

    public RetrofitFactory() {
    }

    private static OkHttpClient client = new OkHttpClient.Builder()
            .writeTimeout(TIME_OUT, TimeUnit.SECONDS)
            .readTimeout(TIME_OUT, TimeUnit.SECONDS)
            .connectTimeout(TIME_OUT, TimeUnit.SECONDS)
            .build();
    private static Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(BASE_URL)
            //.addConverterFactory(GsonConverterFactory.create())
            .addConverterFactory(SimpleXmlConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(client)
            .build();

    public static ApiService getInstance() {
        return retrofit.create(ApiService.class);
    }
}

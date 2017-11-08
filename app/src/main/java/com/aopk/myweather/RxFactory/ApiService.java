package com.aopk.myweather.RxFactory;

import com.aopk.myweather.javabeanXml.BaseEntity;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Administrator on 2017/10/25.
 */

public interface ApiService {
    @GET("WeatherApi")
    Observable<BaseEntity> getWeatherInfo(@Query("city") String city);
}

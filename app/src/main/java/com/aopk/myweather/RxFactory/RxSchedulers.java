package com.aopk.myweather.RxFactory;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;

/**
 * Created by Administrator on 2017/10/25.
 */

public class RxSchedulers {
    public static <T>ObservableTransformer<T,T> compose(){
        return new ObservableTransformer<T, T>() {

            @Override
            public ObservableSource<T> apply(@NonNull Observable<T> upstream) {
                return upstream.subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread());
            }
        };
    }
}

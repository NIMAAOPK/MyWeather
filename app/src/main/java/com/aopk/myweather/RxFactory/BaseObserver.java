package com.aopk.myweather.RxFactory;

import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;

/**
 * Created by Administrator on 2017/10/25.
 */

public abstract class BaseObserver<T> implements Observer<T> {
    private Disposable d;

    @Override
    public void onSubscribe(@NonNull Disposable d) {
        this.d = d;
    }

    @Override
    public void onNext(@NonNull T t) {
        onSuccess(t);
    }

    @Override
    public void onError(@NonNull Throwable e) {
        onFailure(e);
        d.dispose();
    }

    @Override
    public void onComplete() {

    }

   protected abstract void onSuccess(T t);

   protected abstract void onFailure(Throwable e);
}

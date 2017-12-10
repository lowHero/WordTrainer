package com.nz2dev.wordtrainer.app.utils;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * Created by nz2Dev on 10.12.2017
 */
public class UncheckedObserver<T> implements Observer<T> {
    @Override
    public void onSubscribe(Disposable d) {

    }

    @Override
    public void onNext(T t) {

    }

    @Override
    public void onError(Throwable e) {

    }

    @Override
    public void onComplete() {

    }
}

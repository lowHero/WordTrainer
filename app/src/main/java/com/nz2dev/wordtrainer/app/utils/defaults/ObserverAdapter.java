package com.nz2dev.wordtrainer.app.utils.defaults;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * Created by nz2Dev on 10.12.2017
 */
public interface ObserverAdapter<T> extends Observer<T> {

    @Override
    default void onSubscribe(Disposable d) {

    }

    @Override
    default void onNext(T t) {

    }

    @Override
    default void onError(Throwable e) {

    }

    @Override
    default void onComplete() {

    }
}

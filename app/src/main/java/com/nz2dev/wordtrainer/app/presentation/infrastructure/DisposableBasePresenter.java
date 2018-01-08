package com.nz2dev.wordtrainer.app.presentation.infrastructure;

import android.annotation.SuppressLint;
import android.support.annotation.CallSuper;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * Created by nz2Dev on 05.01.2018
 */
public class DisposableBasePresenter<V> extends BasePresenter<V> {

    private CompositeDisposable compositeDisposable;

    private Map<Integer, Disposable> namedDisposable;

    @CallSuper
    @Override
    protected void onViewReady() {
        super.onViewReady();
        compositeDisposable = new CompositeDisposable();
    }

    @CallSuper
    @Override
    public void detachView() {
        super.detachView();
        if (!compositeDisposable.isDisposed()) {
            compositeDisposable.dispose();
        }
        if (namedDisposable != null) {
            Observable.fromIterable(namedDisposable.values())
                    .doFinally(namedDisposable::clear)
                    .forEach(DisposableBasePresenter::forceDispose);
        }
    }

    protected void manage(Disposable disposable) {
        compositeDisposable.add(disposable);
    }

    protected void unmanage(Disposable disposable) {
        if (disposable != null) {
            forceDispose(disposable);
            compositeDisposable.remove(disposable);
        }
    }

    protected void manage(String key, Disposable disposable) {
        manage(key.hashCode(), disposable);
    }

    @SuppressLint("UseSparseArrays")
    protected void manage(int key, Disposable disposable) {
        if (namedDisposable == null) {
            namedDisposable = new HashMap<>();
        }
        forceDispose(namedDisposable.get(key));
        namedDisposable.put(key, disposable);
    }

    private static void forceDispose(Disposable disposable) {
        if (disposable != null && !disposable.isDisposed()) {
            disposable.dispose();
        }
    }

}

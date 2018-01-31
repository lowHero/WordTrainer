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
    public void onViewRecycled() {
        disposeAll();
    }

    protected void manage(Disposable disposable) {
        if (compositeDisposable == null) {
            compositeDisposable = new CompositeDisposable();
        }
        compositeDisposable.add(disposable);
    }

    protected void manage(Object key, Disposable disposable) {
        manageInternal(key.hashCode(), disposable);
    }

    protected void dispose(Object key) {
        disposeInternal(key.hashCode());
    }

    protected void disposeAll() {
        if (compositeDisposable != null && !compositeDisposable.isDisposed()) {
            compositeDisposable.dispose();
            compositeDisposable = null;
        }
        if (namedDisposable != null && namedDisposable.size() > 0) {
            Observable.fromIterable(namedDisposable.values())
                    .doFinally(namedDisposable::clear)
                    .forEach(DisposableBasePresenter::forceDispose);
        }
    }

    private void manageInternal(int key, Disposable disposable) {
        if (namedDisposable == null) {
            namedDisposable = new HashMap<>();
        }
        forceDispose(namedDisposable.get(key));
        namedDisposable.put(key, disposable);
    }

    private void disposeInternal(int key) {
        if (namedDisposable == null) {
            return;
        }
        forceDispose(namedDisposable.get(key));
    }

    private static void forceDispose(Disposable disposable) {
        if (disposable != null && !disposable.isDisposed()) {
            disposable.dispose();
        }
    }

}

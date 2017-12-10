package com.nz2dev.wordtrainer.domain.execution;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Single;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableSingleObserver;

/**
 * Created by nz2Dev on 06.12.2017
 */
@Singleton
public class ExecutionManager {

    private BackgroundExecutor backgroundExecutor;
    private UIExecutor uiExecutor;

    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    @Inject
    public ExecutionManager(BackgroundExecutor backgroundExecutor, UIExecutor uiExecutor) {
        this.backgroundExecutor = backgroundExecutor;
        this.uiExecutor = uiExecutor;
    }

    public <T> void executeInBackground(Single<T> observable, DisposableSingleObserver<T> observer) {
        if(compositeDisposable == null || compositeDisposable.isDisposed()) {
            compositeDisposable = new CompositeDisposable();
        }

        Single<T> signedObservable = observable
                .subscribeOn(backgroundExecutor.getScheduler())
                .observeOn(uiExecutor.getScheduler());

        compositeDisposable.add(signedObservable.subscribeWith(observer));
    }

    public void dispose() {
        if (!compositeDisposable.isDisposed()) {
            compositeDisposable.dispose();
        }
    }
}

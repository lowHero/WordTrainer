package com.nz2dev.wordtrainer.domain.repositories.infrastructure;

import com.nz2dev.wordtrainer.domain.execution.UIExecutor;

import io.reactivex.Observer;
import io.reactivex.subjects.PublishSubject;

/**
 * Created by nz2Dev on 19.12.2017
 */
public class RxStateObservableRepository<T> implements StateObservableRepository<T> {

    private PublishSubject<T> publishSubject = PublishSubject.create();

    protected void requestItemChanged(T item) {
        publishSubject.onNext(item);
    }

    @Override
    public void listenUpdates(Observer<T> observer, UIExecutor uiExecutor) {
        publishSubject.observeOn(uiExecutor.getScheduler()).subscribe(observer);
    }
}

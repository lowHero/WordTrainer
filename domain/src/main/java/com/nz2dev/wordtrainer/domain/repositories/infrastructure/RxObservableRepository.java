package com.nz2dev.wordtrainer.domain.repositories.infrastructure;

import com.nz2dev.wordtrainer.domain.execution.UIExecutor;

import java.util.Collection;

import io.reactivex.Observer;
import io.reactivex.subjects.PublishSubject;

/**
 * Created by nz2Dev on 16.12.2017
 */
public class RxObservableRepository<T> implements ObservableRepository<T> {

    private PublishSubject<Collection<T>> publishSubject = PublishSubject.create();

    protected void requestChanges(Collection<T> newState) {
        publishSubject.onNext(newState);
    }

    @Override
    public void listenChanges(Observer<Collection<T>> changesObserver, UIExecutor uiExecutor) {
        publishSubject.observeOn(uiExecutor.getScheduler()).subscribe(changesObserver);
    }
}

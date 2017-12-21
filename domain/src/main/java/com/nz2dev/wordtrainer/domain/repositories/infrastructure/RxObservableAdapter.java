package com.nz2dev.wordtrainer.domain.repositories.infrastructure;

import com.nz2dev.wordtrainer.domain.execution.UIExecutor;

import java.util.Collection;

import io.reactivex.Observer;
import io.reactivex.subjects.PublishSubject;

/**
 * Created by nz2Dev on 19.12.2017
 */
public class RxObservableAdapter<T> implements ObservableRepository<T>, StateObservableRepository<T> {

    private PublishSubject<Collection<T>> structuralChangesSubject = PublishSubject.create();
    private PublishSubject<T> dataChangesSubject = PublishSubject.create();

    protected void requestItemChanged(T item) {
        dataChangesSubject.onNext(item);
    }

    protected void requestChanges(Collection<T> newState) {
        structuralChangesSubject.onNext(newState);
    }

    @Override
    public void listenUpdates(Observer<T> observer, UIExecutor uiExecutor) {
        dataChangesSubject.observeOn(uiExecutor.getScheduler()).subscribe(observer);
    }

    @Override
    public void listenChanges(Observer<Collection<T>> changesObserver, UIExecutor uiExecutor) {
        structuralChangesSubject.observeOn(uiExecutor.getScheduler()).subscribe(changesObserver);
    }
}

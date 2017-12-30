package com.nz2dev.wordtrainer.domain.repositories.infrastructure;

import com.nz2dev.wordtrainer.domain.execution.UIExecutor;

import java.util.Collection;

import io.reactivex.Observer;
import io.reactivex.Single;
import io.reactivex.functions.Consumer;
import io.reactivex.subjects.PublishSubject;

/**
 * Created by nz2Dev on 16.12.2017
 */
public abstract class RxObservableRepository implements ObservableRepository {

    private PublishSubject<State> publishSubject = PublishSubject.create();

    protected void requestChanges(State state) {
        publishSubject.onNext(state);
    }

    @Override
    public void listenChanges(Consumer<State> changesObserver, UIExecutor uiExecutor) {
        publishSubject.observeOn(uiExecutor.getScheduler()).subscribe(changesObserver);
    }
}

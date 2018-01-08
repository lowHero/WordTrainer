package com.nz2dev.wordtrainer.domain.repositories.infrastructure;

import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;

/**
 * Created by nz2Dev on 16.12.2017
 */
public abstract class RxObservableRepository implements ObservableRepository {

    private PublishSubject<ChangesType> publishSubject = PublishSubject.create();

    protected void requestChanges(ChangesType changesType) {
        publishSubject.onNext(changesType);
    }

    @Override
    public Observable<ChangesType> getChangesPublisher() {
        return publishSubject;
    }

}

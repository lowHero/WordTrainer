package com.nz2dev.wordtrainer.domain.repositories.infrastructure;

import io.reactivex.Observable;

/**
 * Created by nz2Dev on 16.12.2017
 */
public interface ObservableRepository {

    enum ChangesType {
        Changed,
        Updated
    }

    Observable<ChangesType> getChangesPublisher();

}

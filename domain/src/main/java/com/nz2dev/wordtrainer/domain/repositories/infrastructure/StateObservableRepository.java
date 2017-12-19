package com.nz2dev.wordtrainer.domain.repositories.infrastructure;

import com.nz2dev.wordtrainer.domain.execution.UIExecutor;

import io.reactivex.Observer;

/**
 * Created by nz2Dev on 19.12.2017
 */
public interface StateObservableRepository<T> {

    /**
     * observe any internal object changes for objects in repository
     *
     * @param observer observer that will be observer any data changing.
     * @param uiExecutor executor that will be execute callback on main thread.
     */
    void listenUpdates(Observer<T> observer, UIExecutor uiExecutor);

}

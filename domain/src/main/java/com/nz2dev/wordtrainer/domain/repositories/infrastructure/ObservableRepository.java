package com.nz2dev.wordtrainer.domain.repositories.infrastructure;

import com.nz2dev.wordtrainer.domain.execution.UIExecutor;

import java.util.Collection;

import io.reactivex.Observer;

/**
 * Created by nz2Dev on 16.12.2017
 */
public interface ObservableRepository<T> {

    /**
     * listen to any data INSERT/REMOVE operation in repository during lifetime
     * <br>
     * if you don't want to care about disposing yours observers just put inside {@link io.reactivex.observers.DisposableSingleObserver}
     * @param changesObserver observer that will be observe changes.
     * @param uiExecutor executor that will be execute callback on main thread.
     */
    void listenChanges(Observer<Collection<T>> changesObserver, UIExecutor uiExecutor);

}

package com.nz2dev.wordtrainer.domain.repositories.infrastructure;

import com.nz2dev.wordtrainer.domain.execution.UIExecutor;

import io.reactivex.functions.Consumer;

/**
 * Created by nz2Dev on 16.12.2017
 */
public interface ObservableRepository {

    enum State {
        Changed,
        Updated
    }

    /**
     * listen to any data INSERT/REMOVE operation in repository during lifetime
     * <br>
     * if you don't want to care about disposing yours observers just put inside {@link io.reactivex.observers.DisposableSingleObserver}
     * @param stateConsumer Consumer that will be observe changes.
     * @param uiExecutor executor that will be execute callback on main thread.
     */
    void listenChanges(Consumer<State> stateConsumer, UIExecutor uiExecutor);

}

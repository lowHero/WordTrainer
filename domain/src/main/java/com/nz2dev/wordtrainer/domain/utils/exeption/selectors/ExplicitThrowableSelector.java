package com.nz2dev.wordtrainer.domain.utils.exeption.selectors;

import com.nz2dev.wordtrainer.domain.utils.exeption.ThrowableSelector;

import io.reactivex.functions.Consumer;

/**
 * Created by nz2Dev on 26.01.2018
 */
public class ExplicitThrowableSelector<T extends Throwable> implements ThrowableSelector {

    private Class<T> type;
    private Consumer<T> consumer;

    public ExplicitThrowableSelector(Class<T> type, Consumer<T> consumer) {
        this.type = type;
        this.consumer = consumer;
    }

    @Override
    public boolean isHandle(Throwable throwable) {
        return throwable.getClass().isAssignableFrom(type);
    }

    @Override
    public void handle(Throwable filteredThrowable) throws Throwable {
        consumer.accept((T) filteredThrowable);
    }

}

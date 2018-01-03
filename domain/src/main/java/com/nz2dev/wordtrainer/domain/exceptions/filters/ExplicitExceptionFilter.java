package com.nz2dev.wordtrainer.domain.exceptions.filters;

import com.nz2dev.wordtrainer.domain.exceptions.ExceptionHelper.HandledThrowableFilter;

/**
 * Created by nz2Dev on 03.01.2018
 */
public abstract class ExplicitExceptionFilter<T extends Throwable> implements HandledThrowableFilter {

    private Class<T> explicitClass;

    public ExplicitExceptionFilter(Class<T> explicitClass) {
        this.explicitClass = explicitClass;
    }

    @Override
    public boolean filter(Throwable throwable) {
        return throwable.getClass().isAssignableFrom(explicitClass);
    }

    @SuppressWarnings("unchecked")
    @Override
    public void handle(Throwable filteredThrowable) {
        handleExplicit((T) filteredThrowable);
    }

    protected abstract void handleExplicit(T explicitThrowable);

}

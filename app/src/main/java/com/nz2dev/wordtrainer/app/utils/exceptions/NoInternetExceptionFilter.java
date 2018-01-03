package com.nz2dev.wordtrainer.app.utils.exceptions;

import com.nz2dev.wordtrainer.data.exceptions.NoInternetException;
import com.nz2dev.wordtrainer.domain.exceptions.ExceptionHelper;

/**
 * Created by nz2Dev on 03.01.2018
 */
public abstract class NoInternetExceptionFilter implements ExceptionHelper.HandledThrowableFilter {

    @Override
    public final boolean filter(Throwable throwable) {
        return throwable instanceof NoInternetException;
    }

    @Override
    public final void handle(Throwable filteredThrowable) {
        handleNoInternetException((NoInternetException) filteredThrowable);
    }

    protected abstract void handleNoInternetException(NoInternetException exception);

}

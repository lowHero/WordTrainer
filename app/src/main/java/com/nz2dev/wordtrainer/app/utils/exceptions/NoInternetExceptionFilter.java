package com.nz2dev.wordtrainer.app.utils.exceptions;

import com.nz2dev.wordtrainer.domain.exceptions.NoInternetException;
import com.nz2dev.wordtrainer.domain.execution.ExceptionHelper;

import io.reactivex.functions.Consumer;

/**
 * Created by nz2Dev on 03.01.2018
 */
public abstract class NoInternetExceptionFilter implements ExceptionHelper.HandledThrowableFilter {

    public static NoInternetExceptionFilter noInternet(Consumer<NoInternetException> consumer) {
        return new NoInternetExceptionFilter() {
            @Override
            protected void handleNoInternetException(NoInternetException exception) throws Exception {
                consumer.accept(exception);
            }
        };
    }

    @Override
    public final boolean filter(Throwable throwable) {
        return throwable instanceof NoInternetException;
    }

    @Override
    public final void handle(Throwable filteredThrowable) throws Exception {
        handleNoInternetException((NoInternetException) filteredThrowable);
    }

    protected abstract void handleNoInternetException(NoInternetException exception) throws Exception;

}

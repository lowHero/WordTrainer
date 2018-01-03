package com.nz2dev.wordtrainer.domain.exceptions;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.functions.BiConsumer;
import io.reactivex.functions.Consumer;

/**
 * Created by nz2Dev on 03.01.2018
 */
@Singleton
public class ExceptionHelper {

    private ExceptionHandler exceptionHandler;

    @Inject
    public ExceptionHelper(ExceptionHandler exceptionHandler) {
        this.exceptionHandler = exceptionHandler;
    }

    public ExceptionHandler getHandler() {
        return exceptionHandler;
    }

    public <T> BiConsumer<T, Throwable> obtainSafeCallback(Consumer<T> safeConsumer) {
        return (subject, throwable) -> {
            if (throwable != null) {
                exceptionHandler.handleThrowable(throwable);
            } else {
                try {
                    safeConsumer.accept(subject);
                } catch (Exception e) {
                    exceptionHandler.handleThrowable("callback fail", e);
                }
            }
        };
    }

}

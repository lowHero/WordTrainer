package com.nz2dev.wordtrainer.domain.exceptions;

import java.util.ArrayList;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.functions.BiConsumer;
import io.reactivex.functions.Consumer;

/**
 * Created by nz2Dev on 03.01.2018
 */
@Singleton
public class ExceptionHelper {

    public interface HandledThrowableFilter {

        boolean filter(Throwable throwable);

        void handle(Throwable filteredThrowable);

    }

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

    public <T> BiConsumer<T, Throwable> obtainSafeCallback(Consumer<T> safeConsumer,
                                                           HandledThrowableFilter filter) {
        return (subject, throwable) -> {
            if (throwable != null) {
                if (filter.filter(throwable)) {
                    filter.handle(throwable);
                } else {
                    exceptionHandler.handleThrowable("unhandled exception: ", throwable);
                }
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

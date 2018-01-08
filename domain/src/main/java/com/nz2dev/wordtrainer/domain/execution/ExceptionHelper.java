package com.nz2dev.wordtrainer.domain.execution;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.functions.Consumer;

/**
 * Created by nz2Dev on 03.01.2018
 */
@Singleton
public class ExceptionHelper {

    public interface HandledThrowableFilter {

        boolean filter(Throwable throwable);

        void handle(Throwable filteredThrowable) throws Exception;

    }

    private ExceptionHandler exceptionHandler;

    @Inject
    public ExceptionHelper(ExceptionHandler exceptionHandler) {
        this.exceptionHandler = exceptionHandler;
    }

    public ExceptionHandler getHandler() {
        return exceptionHandler;
    }

    public Consumer<Throwable> errorFilter(HandledThrowableFilter... filters) {
        return throwable -> {
            if (throwable != null) {
                if (filters != null) {
                    for (HandledThrowableFilter filter : filters) {
                        if (filter != null && filter.filter(throwable)) {
                            filter.handle(throwable);
                            return;
                        }
                    }
                }
                exceptionHandler.handleThrowable("unhandled exception: ", throwable);
            }
        };
    }

}

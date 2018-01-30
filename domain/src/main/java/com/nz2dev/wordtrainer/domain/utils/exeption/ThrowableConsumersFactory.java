package com.nz2dev.wordtrainer.domain.utils.exeption;

import io.reactivex.functions.Consumer;

/**
 * Created by nz2Dev on 26.01.2018
 */
public class ThrowableConsumersFactory {

    public static Consumer<Throwable> handle(ThrowableSelector... selectors) {
        return throwable -> {
            if (throwable != null) {
                if (selectors != null) {
                    for (ThrowableSelector selector : selectors) {
                        if (selector != null && selector.isHandle(throwable)) {
                            try {
                                selector.handle(throwable);
                            } catch (Throwable handlingThrowable) {
                                uncaught(new FailedToHandleException(handlingThrowable));
                            }
                            return;
                        }
                    }
                }
                uncaught(new NoSuchSelectorException(throwable));
            }
        };
    }

    private static void uncaught(Throwable throwable) {
        Thread currentThread = Thread.currentThread();
        Thread.UncaughtExceptionHandler handler = currentThread.getUncaughtExceptionHandler();
        handler.uncaughtException(currentThread, throwable);
    }

}

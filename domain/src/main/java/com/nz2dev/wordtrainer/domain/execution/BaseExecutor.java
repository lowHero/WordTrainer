package com.nz2dev.wordtrainer.domain.execution;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * Created by nz2Dev on 05.01.2018
 */
public abstract class BaseExecutor<T> {

    public static class DisposableManipulator {

        private Disposable disposable;

        public DisposableManipulator(Disposable disposable) {
            this.disposable = disposable;
        }

        public void putInComposite(CompositeDisposable compositeDisposable) {
            compositeDisposable.add(disposable);
        }

        public Disposable getDisposable() {
            return disposable;
        }

        public void dispose() {
            disposable.dispose();
        }

    }

    private ExceptionHelper exceptionHelper;
    private Consumer<Throwable> errorConsumer;

    private boolean executed = false;

    protected BaseExecutor(ExceptionHelper exceptionHelper) {
        this.exceptionHelper = exceptionHelper;
    }

    public final BaseExecutor<T> whenThrow(Consumer<Throwable> consumer) {
        errorConsumer = consumer;
        return this;
    }

    public final BaseExecutor<T> whenFilteredThrow(ExceptionHelper.HandledThrowableFilter... filters) {
        errorConsumer = exceptionHelper.errorFilter(filters);
        return this;
    }

    public final DisposableManipulator execute(Consumer<T> consumer) {
        return execute(consumer, errorConsumer);
    }

    public final DisposableManipulator execute(Consumer<T> consumer, Consumer<Throwable> errorConsumer) {
        if (executed) {
            throw new RuntimeException("is already executed");
        }
        executed = true;
        return new DisposableManipulator(executeInternal(consumer, errorConsumer));
    }

    protected abstract Disposable executeInternal(Consumer<T> consumer, Consumer<Throwable> throwableConsumer);

}

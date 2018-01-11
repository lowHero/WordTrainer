package com.nz2dev.wordtrainer.domain.javarx;

import org.junit.Test;

import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.plugins.RxJavaPlugins;

/**
 * Created by nz2Dev on 03.01.2018
 */
public class ConsumerTest {

    @Test
    public void subscribe_withBiConsumer_ShouldHandleThrowable() {
        Single.just("A")
                .subscribe((o, throwable) -> {
                    if (throwable != null) {
                        System.out.println("throwable callback");
                    } else {
                        System.out.println("normal callback");
                    }
                });
    }

    @Test
    public void subscribe_withRuntimeExceptionThrow_ShouldHandlWithOnError() {
        Single.zip(
                Single.just("A")
                        .doOnSuccess(s -> {
                            throw new RuntimeException("A");
                        }),
                Single.just("B"),
                (o, s) -> "ASD")
        .subscribe(s -> {}, System.out::println);
    }

}

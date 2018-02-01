package com.nz2dev.wordtrainer.domain.javarx.api;

import org.junit.Test;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by nz2Dev on 21.01.2018
 */
public class DisposingTest {

    @Test
    public void test() throws Exception {
        Disposable disposable = Observable.create(e -> {
            Observable.interval(0, 50, TimeUnit.MILLISECONDS, Schedulers.newThread())
                    .subscribe(t -> {
                        if (e.isDisposed()) {
                            print("disposed i: " + t);
                        } else {
                            print("i: " + t);
                        }
                    });
        }).subscribeOn(Schedulers.newThread())
                .doOnDispose(() -> {
                    print("do on dispose");
                })
                .subscribe();

        Thread.sleep(100);
        print("dispose...");
        disposable.dispose();
    }

    private static void print(String s) {
        System.out.println(s);
    }

}

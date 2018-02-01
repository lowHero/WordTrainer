package com.nz2dev.wordtrainer.domain.javarx.operators.common;

import org.junit.Test;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;

import static java.lang.System.out;

/**
 * Created by nz2Dev on 31.01.2018
 */
public class CommonCases {

    @Test
    public void foo() throws Exception {
        Observable.fromArray(1, 2, 3)
                .switchMap(integer -> Observable
                        .just(integer)
                        .delay(integer, TimeUnit.SECONDS)
                        .subscribeOn(Schedulers.newThread()))
                .doOnNext(out::println)
                .subscribeOn(Schedulers.newThread())
                .subscribe();

        Thread.sleep(4000);
    }

}

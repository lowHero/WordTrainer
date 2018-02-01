package com.nz2dev.wordtrainer.domain.javarx.operators.flatMap;

import org.junit.Test;

import io.reactivex.Observable;

import static java.lang.System.out;

/**
 * Created by nz2Dev on 31.01.2018
 */
public class FlatMapTest {

    @Test
    public void foo() {
        Observable.just(1, 2, 3)
                .flatMap(integer -> {
                    out.println();
                    return Observable.range(0, integer);
                })
                .doOnNext(out::print)
                .subscribe();
    }

}

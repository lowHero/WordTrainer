package com.nz2dev.wordtrainer.domain.javarx.operators.generate;

import org.junit.Test;

import io.reactivex.Observable;

import static java.lang.System.out;

/**
 * Created by nz2Dev on 31.01.2018
 */
public class GenerateTest {

    @Test
    public void foo() {
        Observable.generate(emitter -> {
            emitter.onNext(1D);
        }).take(5)
                .doOnNext(out::println)
                .subscribe();
    }

}

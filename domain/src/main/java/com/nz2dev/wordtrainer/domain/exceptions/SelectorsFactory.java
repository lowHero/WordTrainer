package com.nz2dev.wordtrainer.domain.exceptions;

import com.nz2dev.wordtrainer.domain.utils.exeption.ThrowableSelector;
import com.nz2dev.wordtrainer.domain.utils.exeption.selectors.ExplicitThrowableSelector;

import io.reactivex.functions.Consumer;

/**
 * Created by nz2Dev on 30.01.2018
 */
public class SelectorsFactory {

    public static ThrowableSelector ifNotEnoughtWordForTraining(Consumer<NotEnoughWordForTraining> consumer) {
        return new ExplicitThrowableSelector<>(NotEnoughWordForTraining.class, consumer);
    }

}

package com.nz2dev.wordtrainer.domain.utils.exeption;

/**
 * Created by nz2Dev on 26.01.2018
 */
public interface ThrowableSelector {

    boolean isHandle(Throwable throwable);

    void handle(Throwable throwable) throws Throwable;

}

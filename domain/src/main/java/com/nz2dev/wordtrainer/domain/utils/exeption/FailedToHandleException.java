package com.nz2dev.wordtrainer.domain.utils.exeption;

/**
 * Created by nz2Dev on 29.01.2018
 */
public class FailedToHandleException extends Exception {

    public FailedToHandleException(Throwable throwable) {
        super(throwable);
    }
}

package com.nz2dev.wordtrainer.domain.execution;

/**
 * Created by nz2Dev on 01.01.2018
 */
public interface ExceptionHandler {

    void handleThrowable(Throwable throwable);

    void handleThrowable(String title, Throwable throwable);

}

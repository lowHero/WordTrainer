package com.nz2dev.wordtrainer.data.exceptions;

/**
 * Created by nz2Dev on 03.01.2018
 */
public class NoInternetException extends RuntimeException {

    public NoInternetException() {
        super("No internet connection");
    }
}

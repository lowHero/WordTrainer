package com.nz2dev.wordtrainer.domain.exceptions;

/**
 * Created by nz2Dev on 07.01.2018
 */
public class NotImplementedException extends RuntimeException {

    public NotImplementedException() {
    }

    public NotImplementedException(String description) {
        super(description);
    }
}

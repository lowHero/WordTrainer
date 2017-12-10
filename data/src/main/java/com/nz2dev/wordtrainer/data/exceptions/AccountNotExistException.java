package com.nz2dev.wordtrainer.data.exceptions;

/**
 * Created by nz2Dev on 10.12.2017
 */
public class AccountNotExistException extends RuntimeException {

    public AccountNotExistException(String name) {
        super(String.format("account %s not exist in db", name));
    }
}

package com.nz2dev.wordtrainer.domain.exceptions;

/**
 * Created by nz2Dev on 10.12.2017
 */
public class AccountNotExistOrPasswordIncorrectException extends Exception {

    public AccountNotExistOrPasswordIncorrectException(String name) {
        super(String.format("account %s not exist or password is incorrect", name));
    }
}

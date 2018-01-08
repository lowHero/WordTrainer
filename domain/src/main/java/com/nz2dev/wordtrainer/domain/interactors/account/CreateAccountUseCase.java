package com.nz2dev.wordtrainer.domain.interactors.account;

import com.nz2dev.wordtrainer.domain.execution.ExceptionHandler;
import com.nz2dev.wordtrainer.domain.execution.ExecutionProxy;
import com.nz2dev.wordtrainer.domain.models.Account;
import com.nz2dev.wordtrainer.domain.repositories.AccountRepository;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Single;

/**
 * Created by nz2Dev on 05.01.2018
 */
@Singleton
public class CreateAccountUseCase {

    private final AccountRepository accountRepository;
    private final ExecutionProxy executionProxy;
    private final ExceptionHandler handler;

    @Inject
    public CreateAccountUseCase(AccountRepository accountRepository, ExecutionProxy executionProxy, ExceptionHandler handler) {
        this.accountRepository = accountRepository;
        this.executionProxy = executionProxy;
        this.handler = handler;
    }

    public Single<Boolean> execute(String name, String password) {
        return accountRepository.addAccount(Account.unidentified(name), password)
                .subscribeOn(executionProxy.background())
                .observeOn(executionProxy.ui())
                .doOnError(handler::handleThrowable);
    }

}

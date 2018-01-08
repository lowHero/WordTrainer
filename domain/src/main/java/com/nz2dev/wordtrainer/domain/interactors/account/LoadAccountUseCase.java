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
public class LoadAccountUseCase {

    private final AccountRepository accountRepository;
    private final ExecutionProxy executionProxy;
    private final ExceptionHandler exceptionHandler;

    @Inject
    public LoadAccountUseCase(AccountRepository accountRepository, ExecutionProxy executionProxy, ExceptionHandler exceptionHandler) {
        this.accountRepository = accountRepository;
        this.executionProxy = executionProxy;
        this.exceptionHandler = exceptionHandler;
    }

    public Single<Account> execute(String accountName) {
        return accountRepository.getAccount(accountName)
                .subscribeOn(executionProxy.background())
                .observeOn(executionProxy.ui())
                .doOnError(exceptionHandler::handleThrowable);
    }

}

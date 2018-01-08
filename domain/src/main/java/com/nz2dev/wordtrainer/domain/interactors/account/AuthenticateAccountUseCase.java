package com.nz2dev.wordtrainer.domain.interactors.account;

import com.nz2dev.wordtrainer.domain.execution.ExecutionProxy;
import com.nz2dev.wordtrainer.domain.models.Account;
import com.nz2dev.wordtrainer.domain.models.AccountHistory;
import com.nz2dev.wordtrainer.domain.preferences.AppPreferences;
import com.nz2dev.wordtrainer.domain.repositories.AccountHistoryRepository;
import com.nz2dev.wordtrainer.domain.repositories.AccountRepository;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Single;

/**
 * Created by nz2Dev on 08.01.2018
 */
@Singleton
public class AuthenticateAccountUseCase {

    private final AccountRepository accountRepository;
    private final AccountHistoryRepository accountHistoryRepository;
    private final AppPreferences appPreferences;
    private final ExecutionProxy executionProxy;

    @Inject
    public AuthenticateAccountUseCase(AccountRepository accountRepository, AccountHistoryRepository accountHistoryRepository, AppPreferences appPreferences, ExecutionProxy executionProxy) {
        this.accountRepository = accountRepository;
        this.accountHistoryRepository = accountHistoryRepository;
        this.appPreferences = appPreferences;
        this.executionProxy = executionProxy;
    }

    public Single<Boolean> execute(String name, String password) {
        return accountRepository.getAccount(name, password)
                .map(account -> {
                    appPreferences.signIn(account, password);
                    boolean result = accountHistoryRepository.addRecord(AccountHistory.createNow(name)).blockingGet();
                    if (!result) {
                        throw new RuntimeException("can't add accountHistory");
                    }
                    return true;
                })
                .subscribeOn(executionProxy.background())
                .observeOn(executionProxy.ui());
    }

}
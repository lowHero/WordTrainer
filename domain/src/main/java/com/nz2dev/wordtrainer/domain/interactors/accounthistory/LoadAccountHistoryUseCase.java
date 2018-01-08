package com.nz2dev.wordtrainer.domain.interactors.accounthistory;

import com.nz2dev.wordtrainer.domain.execution.ExceptionHandler;
import com.nz2dev.wordtrainer.domain.execution.ExecutionProxy;
import com.nz2dev.wordtrainer.domain.models.Account;
import com.nz2dev.wordtrainer.domain.models.AccountHistory;
import com.nz2dev.wordtrainer.domain.repositories.AccountHistoryRepository;
import com.nz2dev.wordtrainer.domain.repositories.AccountRepository;

import java.util.Collection;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Observable;
import io.reactivex.Single;

/**
 * Created by nz2Dev on 05.01.2018
 */
@Singleton
public class LoadAccountHistoryUseCase {

    private final AccountHistoryRepository historyRepository;
    private final AccountRepository accountRepository;
    private final ExecutionProxy executionProxy;
    private final ExceptionHandler handler;

    @Inject
    public LoadAccountHistoryUseCase(AccountHistoryRepository historyRepository,
                                     AccountRepository accountRepository,
                                     ExecutionProxy executionProxy,
                                     ExceptionHandler handler) {
        this.historyRepository = historyRepository;
        this.accountRepository = accountRepository;
        this.executionProxy = executionProxy;
        this.handler = handler;
    }

    public Single<Collection<Account>> execute() {
        return historyRepository.getAllRecords()
                .map(accountHistories -> {
                    List<String> namesList = Observable
                            .fromIterable(accountHistories)
                            .map(AccountHistory::getAccountName)
                            .toList()
                            .blockingGet();

                    String[] namesArray = namesList.toArray(new String[namesList.size()]);
                    return accountRepository.getAccounts(namesArray).blockingGet();
                })
                .subscribeOn(executionProxy.background())
                .observeOn(executionProxy.ui())
                .doOnError(handler::handleThrowable);
    }

}

package com.nz2dev.wordtrainer.domain.interactors.accounthistory;

import com.nz2dev.wordtrainer.domain.data.repositories.AccountHistoryRepository;
import com.nz2dev.wordtrainer.domain.data.repositories.AccountRepository;
import com.nz2dev.wordtrainer.domain.device.SchedulersFacade;
import com.nz2dev.wordtrainer.domain.models.Account;
import com.nz2dev.wordtrainer.domain.models.AccountHistory;

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
    private final SchedulersFacade schedulersFacade;

    @Inject
    public LoadAccountHistoryUseCase(AccountHistoryRepository historyRepository, AccountRepository accountRepository, SchedulersFacade schedulersFacade) {
        this.historyRepository = historyRepository;
        this.accountRepository = accountRepository;
        this.schedulersFacade = schedulersFacade;
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
                .subscribeOn(schedulersFacade.background())
                .observeOn(schedulersFacade.ui());
    }

}

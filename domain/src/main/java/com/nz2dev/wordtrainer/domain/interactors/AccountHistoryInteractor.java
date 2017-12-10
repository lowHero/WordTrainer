package com.nz2dev.wordtrainer.domain.interactors;

import com.nz2dev.wordtrainer.domain.execution.BackgroundExecutor;
import com.nz2dev.wordtrainer.domain.execution.UIExecutor;
import com.nz2dev.wordtrainer.domain.models.Account;
import com.nz2dev.wordtrainer.domain.models.AccountHistory;
import com.nz2dev.wordtrainer.domain.repositories.AccountHistoryRepository;
import com.nz2dev.wordtrainer.domain.repositories.AccountRepository;

import java.util.Collection;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Observable;
import io.reactivex.SingleObserver;
import io.reactivex.observers.DisposableSingleObserver;

/**
 * Created by nz2Dev on 07.12.2017
 */
@Singleton
public class AccountHistoryInteractor {

    private AccountHistoryRepository historyRepository;
    private AccountRepository accountRepository;
    private UIExecutor uiExecutor;
    private BackgroundExecutor backgroundExecutor;

    @Inject
    public AccountHistoryInteractor(AccountHistoryRepository historyRepository, AccountRepository accountRepository, UIExecutor uiExecutor, BackgroundExecutor backgroundExecutor) {
        this.historyRepository = historyRepository;
        this.accountRepository = accountRepository;
        this.uiExecutor = uiExecutor;
        this.backgroundExecutor = backgroundExecutor;
    }

    public void createRecord(Account account, SingleObserver<Boolean> observer) {
        AccountHistory history = AccountHistory.createNow(account.getName());
        historyRepository.addRecord(history)
                .subscribeOn(backgroundExecutor.getScheduler())
                .observeOn(uiExecutor.getScheduler())
                .subscribe(observer);
    }

    public void loadHistory(SingleObserver<Collection<Account>> observer) {
        historyRepository.getAllRecords()
                .map(accountHistories -> {
                    List<String> strings = Observable
                            .fromIterable(accountHistories)
                            .map(AccountHistory::getAccountName)
                            .toList()
                            .blockingGet();
                    return accountRepository.getAccounts(strings.toArray(new String[strings.size()])).blockingGet();
                })
                .subscribeOn(backgroundExecutor.getScheduler())
                .observeOn(uiExecutor.getScheduler())
                .subscribe(observer);
    }
}

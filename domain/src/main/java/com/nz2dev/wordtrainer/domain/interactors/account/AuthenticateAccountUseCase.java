package com.nz2dev.wordtrainer.domain.interactors.account;

import com.nz2dev.wordtrainer.domain.device.SchedulersFacade;
import com.nz2dev.wordtrainer.domain.models.AccountHistory;
import com.nz2dev.wordtrainer.domain.data.preferences.AppPreferences;
import com.nz2dev.wordtrainer.domain.data.repositories.AccountHistoryRepository;
import com.nz2dev.wordtrainer.domain.data.repositories.AccountRepository;

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
    private final SchedulersFacade schedulersFacade;

    @Inject
    public AuthenticateAccountUseCase(AccountRepository accountRepository, AccountHistoryRepository accountHistoryRepository, AppPreferences appPreferences, SchedulersFacade schedulersFacade) {
        this.accountRepository = accountRepository;
        this.accountHistoryRepository = accountHistoryRepository;
        this.appPreferences = appPreferences;
        this.schedulersFacade = schedulersFacade;
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
                .subscribeOn(schedulersFacade.background())
                .observeOn(schedulersFacade.ui());
    }

}
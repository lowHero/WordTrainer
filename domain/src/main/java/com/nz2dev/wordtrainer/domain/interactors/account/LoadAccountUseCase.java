package com.nz2dev.wordtrainer.domain.interactors.account;

import com.nz2dev.wordtrainer.domain.data.repositories.AccountRepository;
import com.nz2dev.wordtrainer.domain.device.SchedulersFacade;
import com.nz2dev.wordtrainer.domain.models.Account;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Single;

/**
 * Created by nz2Dev on 05.01.2018
 */
@Singleton
public class LoadAccountUseCase {

    private final AccountRepository accountRepository;
    private final SchedulersFacade schedulersFacade;

    @Inject
    public LoadAccountUseCase(AccountRepository accountRepository, SchedulersFacade schedulersFacade) {
        this.accountRepository = accountRepository;
        this.schedulersFacade = schedulersFacade;
    }

    public Single<Account> execute(String accountName) {
        return accountRepository.getAccount(accountName)
                .subscribeOn(schedulersFacade.background())
                .observeOn(schedulersFacade.ui());
    }

}

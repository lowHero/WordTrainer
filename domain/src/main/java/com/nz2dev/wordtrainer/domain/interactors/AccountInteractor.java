package com.nz2dev.wordtrainer.domain.interactors;

import com.nz2dev.wordtrainer.domain.execution.ExecutionManager;
import com.nz2dev.wordtrainer.domain.models.Account;
import com.nz2dev.wordtrainer.domain.repositories.AccountRepository;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Single;
import io.reactivex.observers.DisposableSingleObserver;

/**
 * Created by nz2Dev on 30.11.2017
 */
@Singleton
public class AccountInteractor {

    private AccountRepository accountRepository;
    private ExecutionManager executionManager;

    @Inject
    public AccountInteractor(AccountRepository accountRepository, ExecutionManager executionManager) {
        this.accountRepository = accountRepository;
        this.executionManager = executionManager;
    }

    public void createAccount(Account account, String password, DisposableSingleObserver<Boolean> observer) {
        executionManager.executeInBackground(accountRepository.addAccount(account, password), observer);
    }

    public void loadIfPasswordExist(String name, String password, DisposableSingleObserver<Account> observer) {
        executionManager.executeInBackground(accountRepository.getAccount(name, password), observer);
    }

    public void loadIfExist(String name, DisposableSingleObserver<Account> observer) {
        executionManager.executeInBackground(accountRepository.getAccount(name), observer);
    }

}

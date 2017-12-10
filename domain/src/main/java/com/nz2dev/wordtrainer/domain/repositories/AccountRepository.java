package com.nz2dev.wordtrainer.domain.repositories;

import com.nz2dev.wordtrainer.domain.models.Account;

import java.util.Collection;

import io.reactivex.Single;

/**
 * Created by nz2Dev on 30.11.2017
 */
public interface AccountRepository {

    Single<Boolean> addAccount(Account account, String password);
    Single<Account> getAccount(String name, String password);
    Single<Account> getAccount(String name);
    Single<Collection<Account>> getAccounts(String... names);

}

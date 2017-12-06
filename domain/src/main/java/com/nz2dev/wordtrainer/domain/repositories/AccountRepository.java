package com.nz2dev.wordtrainer.domain.repositories;

import com.nz2dev.wordtrainer.domain.models.Account;
import com.nz2dev.wordtrainer.domain.models.Credentials;

import io.reactivex.Single;

/**
 * Created by nz2Dev on 30.11.2017
 */
public interface AccountRepository {

    Single<Boolean> addAccount(Credentials account);
    Single<Account> getAccount(Credentials credentials);

}

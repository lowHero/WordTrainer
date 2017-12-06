package com.nz2dev.wordtrainer.data.repositories;

import com.nz2dev.wordtrainer.data.core.dao.AccountDao;
import com.nz2dev.wordtrainer.data.core.entity.AccountEntity;
import com.nz2dev.wordtrainer.data.mapping.Mapper;
import com.nz2dev.wordtrainer.domain.models.Account;
import com.nz2dev.wordtrainer.domain.models.Credentials;
import com.nz2dev.wordtrainer.domain.repositories.AccountRepository;

import java.io.IOException;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Single;

/**
 * Created by nz2Dev on 06.12.2017
 */
@Singleton
public class RoomAccountRepository implements AccountRepository {

    private AccountDao accountDao;
    private Mapper mapper;

    @Inject
    public RoomAccountRepository(AccountDao accountDao, Mapper mapper) {
        this.accountDao = accountDao;
        this.mapper = mapper;
    }

    @Override
    public Single<Boolean> addAccount(Credentials accountCredentials) {
        return Single.create(emitter -> {
            accountDao.Register(mapper.map(accountCredentials, AccountEntity.class));
            emitter.onSuccess(true);
        });
    }

    @Override
    public Single<Account> getAccount(Credentials credentials) {
        return Single.create(emitter -> {
            AccountEntity entity = accountDao.getByCredentials(credentials.getLogin(), credentials.getPassword());
            emitter.onSuccess(mapper.map(entity, Account.class));
        });
    }
}

package com.nz2dev.wordtrainer.data.repositories;

import com.nz2dev.wordtrainer.data.core.WordTrainerDatabase;
import com.nz2dev.wordtrainer.data.core.dao.AccountDao;
import com.nz2dev.wordtrainer.data.core.entity.AccountEntity;
import com.nz2dev.wordtrainer.domain.exceptions.AccountNotExistException;
import com.nz2dev.wordtrainer.domain.exceptions.AccountNotExistOrPasswordIncorrectException;
import com.nz2dev.wordtrainer.data.mapping.Mapper;
import com.nz2dev.wordtrainer.domain.models.Account;
import com.nz2dev.wordtrainer.domain.data.repositories.AccountRepository;

import java.util.ArrayList;
import java.util.Collection;

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
    public RoomAccountRepository(WordTrainerDatabase database, Mapper mapper) {
        this.accountDao = database.getAccountDao();
        this.mapper = mapper;
    }

    @Override
    public Single<Boolean> addAccount(Account account, String password) {
        return Single.create(emitter -> {
            AccountEntity entity = mapper.map(account, AccountEntity.class).withPassword(password);
            emitter.onSuccess(accountDao.Register(entity) != -1);
        });
    }

    @Override
    public Single<Account> getAccount(String name, String password) {
        return Single.create(emitter -> {
            Account account = mapper.map(accountDao.getByCredentials(name, password), Account.class);
            if (account != null) {
                emitter.onSuccess(account);
            } else {
                emitter.onError(new AccountNotExistOrPasswordIncorrectException(name));
            }
        });
    }

    @Override
    public Single<Account> getAccount(String name) {
        return Single.create(emitter -> {
            Account account = mapper.map(accountDao.getByName(name), Account.class);
            if (account != null) {
                emitter.onSuccess(account);
            } else {
                emitter.onError(new AccountNotExistException(name));
            }
        });
    }

    @Override
    public Single<Collection<Account>> getAccounts(String... names) {
        return Single.create(emitter -> {
            emitter.onSuccess(mapper.mapList(accountDao.getByNames(names), new ArrayList<>(), Account.class));
        });
    }
}

package com.nz2dev.wordtrainer.data.repositories;

import com.nz2dev.wordtrainer.data.core.dao.AccountHistoryDao;
import com.nz2dev.wordtrainer.data.core.entity.AccountHistoryEntity;
import com.nz2dev.wordtrainer.data.mapping.Mapper;
import com.nz2dev.wordtrainer.domain.models.AccountHistory;
import com.nz2dev.wordtrainer.domain.repositories.AccountHistoryRepository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Single;

/**
 * Created by nz2Dev on 10.12.2017
 */
@Singleton
public class RoomAccountHistoryRepository implements AccountHistoryRepository {

    private AccountHistoryDao accountHistoryDao;
    private Mapper mapper;

    @Inject
    public RoomAccountHistoryRepository(AccountHistoryDao accountHistoryDao, Mapper mapper) {
        this.accountHistoryDao = accountHistoryDao;
        this.mapper = mapper;
    }

    @Override
    public Single<Boolean> addRecord(AccountHistory history) {
        return Single.create(emitter -> {
            AccountHistoryEntity entity = mapper.map(history, AccountHistoryEntity.class);
            emitter.onSuccess(accountHistoryDao.add(entity) != -1);
        });
    }

    @Override
    public Single<Collection<AccountHistory>> getAllRecords() {
        return Single.create(emitter -> {
            List<AccountHistoryEntity> historyEntities = accountHistoryDao.getAllHistories();
            List<AccountHistory> histories = mapper.mapList(
                    historyEntities,
                    new ArrayList<>(historyEntities.size()),
                    AccountHistory.class);

            emitter.onSuccess(histories);
        });
    }
}

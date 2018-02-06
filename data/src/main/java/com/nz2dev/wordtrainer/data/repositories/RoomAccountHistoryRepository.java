package com.nz2dev.wordtrainer.data.repositories;

import com.nz2dev.wordtrainer.data.source.local.WordTrainerDatabase;
import com.nz2dev.wordtrainer.data.source.local.dao.AccountHistoryDao;
import com.nz2dev.wordtrainer.data.source.local.entity.AccountHistoryEntity;
import com.nz2dev.wordtrainer.data.source.local.mapping.DatabaseMapper;
import com.nz2dev.wordtrainer.domain.models.AccountHistory;
import com.nz2dev.wordtrainer.domain.data.repositories.AccountHistoryRepository;

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
    private DatabaseMapper databaseMapper;

    @Inject
    public RoomAccountHistoryRepository(WordTrainerDatabase database, DatabaseMapper databaseMapper) {
        this.accountHistoryDao = database.getAccountHistoryDao();
        this.databaseMapper = databaseMapper;
    }

    @Override
    public Single<Boolean> addRecord(AccountHistory history) {
        return Single.create(emitter -> {
            AccountHistoryEntity entity = databaseMapper.map(history, AccountHistoryEntity.class);
            emitter.onSuccess(accountHistoryDao.add(entity) != -1);
        });
    }

    @Override
    public Single<Collection<AccountHistory>> getAllRecords() {
        return Single.create(emitter -> {
            List<AccountHistoryEntity> historyEntities = accountHistoryDao.getAllHistories();
            Collection<AccountHistory> histories = databaseMapper.mapList(
                    historyEntities,
                    new ArrayList<>(historyEntities.size()),
                    AccountHistory.class);

            emitter.onSuccess(histories);
        });
    }
}

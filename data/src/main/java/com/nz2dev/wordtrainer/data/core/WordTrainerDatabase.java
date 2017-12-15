package com.nz2dev.wordtrainer.data.core;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.nz2dev.wordtrainer.data.core.dao.AccountDao;
import com.nz2dev.wordtrainer.data.core.dao.AccountHistoryDao;
import com.nz2dev.wordtrainer.data.core.dao.WordDao;
import com.nz2dev.wordtrainer.data.core.entity.AccountEntity;
import com.nz2dev.wordtrainer.data.core.entity.AccountHistoryEntity;
import com.nz2dev.wordtrainer.data.core.entity.WordEntity;

/**
 * Created by nz2Dev on 28.11.2017
 */
@Database(entities = {AccountEntity.class, AccountHistoryEntity.class, WordEntity.class}, version = 5)
public abstract class WordTrainerDatabase extends RoomDatabase {

    public abstract AccountDao accountDao();

    public abstract AccountHistoryDao accountHistoryDao();

    public abstract WordDao wordDao();

}

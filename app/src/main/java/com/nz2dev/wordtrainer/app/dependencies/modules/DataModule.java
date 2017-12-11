package com.nz2dev.wordtrainer.app.dependencies.modules;

import android.arch.persistence.room.Room;
import android.content.Context;

import com.nz2dev.wordtrainer.data.core.WordTrainerDatabase;
import com.nz2dev.wordtrainer.data.core.dao.AccountDao;
import com.nz2dev.wordtrainer.data.core.dao.AccountHistoryDao;
import com.nz2dev.wordtrainer.data.core.dao.WordDao;
import com.nz2dev.wordtrainer.data.repositories.RoomAccountHistoryRepository;
import com.nz2dev.wordtrainer.data.repositories.RoomAccountRepository;
import com.nz2dev.wordtrainer.data.repositories.RoomWordRepository;
import com.nz2dev.wordtrainer.domain.repositories.AccountHistoryRepository;
import com.nz2dev.wordtrainer.domain.repositories.AccountRepository;
import com.nz2dev.wordtrainer.domain.repositories.WordsRepository;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by nz2Dev on 06.12.2017
 */
@Module
public class DataModule {

    @Provides
    @Singleton
    WordTrainerDatabase provideWordTrainerDatabase(Context context) {
        return Room.databaseBuilder(context, WordTrainerDatabase.class, "WordTrainerDatabase")
                .fallbackToDestructiveMigration()
                .build();
    }

    @Provides
    @Singleton
    AccountDao provideAccountDao(WordTrainerDatabase database) {
        return database.accountDao();
    }

    @Provides
    @Singleton
    AccountHistoryDao provideAccountHistoryDao(WordTrainerDatabase database) {
        return database.accountHistoryDao();
    }

    @Provides
    @Singleton
    WordDao provideWordDao(WordTrainerDatabase database) {
        return database.wordDao();
    }

    @Singleton
    @Provides
    AccountRepository provideAccountRepository(RoomAccountRepository repository) {
        return repository;
    }

    @Singleton
    @Provides AccountHistoryRepository provideAccountHistoryRepository(RoomAccountHistoryRepository repository) {
        return repository;
    }

    @Singleton
    @Provides
    WordsRepository provideWordsRepository(RoomWordRepository repository) {
        return repository;
    }
}

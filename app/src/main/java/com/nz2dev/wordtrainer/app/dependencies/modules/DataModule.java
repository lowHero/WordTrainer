package com.nz2dev.wordtrainer.app.dependencies.modules;

import com.nz2dev.wordtrainer.data.repositories.RoomAccountRepository;
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

    @Singleton
    @Provides
    AccountRepository provideAccountRepository(RoomAccountRepository repository) {
        return repository;
    }

    @Singleton
    @Provides
    WordsRepository provideWordsRepository() {
        return null;
    }
}

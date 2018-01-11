package com.nz2dev.wordtrainer.app.common.dependencies.modules;

import android.arch.persistence.room.Room;
import android.content.Context;

import com.nz2dev.wordtrainer.data.core.WordTrainerDatabase;
import com.nz2dev.wordtrainer.data.core.dao.AccountDao;
import com.nz2dev.wordtrainer.data.core.dao.AccountHistoryDao;
import com.nz2dev.wordtrainer.data.core.dao.CourseDao;
import com.nz2dev.wordtrainer.data.core.dao.TrainingDao;
import com.nz2dev.wordtrainer.data.core.dao.WordDao;
import com.nz2dev.wordtrainer.data.preferences.SharedAppPreferences;
import com.nz2dev.wordtrainer.data.repositories.MemoryLanguageRepository;
import com.nz2dev.wordtrainer.data.repositories.RoomAccountHistoryRepository;
import com.nz2dev.wordtrainer.data.repositories.RoomAccountRepository;
import com.nz2dev.wordtrainer.data.repositories.RoomCourseRepository;
import com.nz2dev.wordtrainer.data.repositories.RoomSchedulingRepository;
import com.nz2dev.wordtrainer.data.repositories.RoomTrainingRepository;
import com.nz2dev.wordtrainer.data.repositories.RoomWordRepository;
import com.nz2dev.wordtrainer.domain.preferences.AppPreferences;
import com.nz2dev.wordtrainer.domain.repositories.AccountHistoryRepository;
import com.nz2dev.wordtrainer.domain.repositories.AccountRepository;
import com.nz2dev.wordtrainer.domain.repositories.CourseRepository;
import com.nz2dev.wordtrainer.domain.repositories.LanguageRepository;
import com.nz2dev.wordtrainer.domain.repositories.SchedulingRepository;
import com.nz2dev.wordtrainer.domain.repositories.TrainingsRepository;
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

    @Singleton
    @Provides
    AccountRepository provideAccountRepository(RoomAccountRepository repository) {
        return repository;
    }

    @Singleton
    @Provides
    AccountHistoryRepository provideAccountHistoryRepository(RoomAccountHistoryRepository repository) {
        return repository;
    }

    @Singleton
    @Provides
    WordsRepository provideWordsRepository(RoomWordRepository repository) {
        return repository;
    }

    @Singleton
    @Provides
    TrainingsRepository provideTrainingsRepository(RoomTrainingRepository repository) {
        return repository;
    }

    @Singleton
    @Provides
    CourseRepository provideCourseRepository(RoomCourseRepository repository) {
        return repository;
    }

    @Singleton
    @Provides
    SchedulingRepository provideSchedulingRepository(RoomSchedulingRepository repository) {
        return repository;
    }

    @Singleton
    @Provides
    LanguageRepository provideLanguageRepository(MemoryLanguageRepository memoryLanguageRepository) {
        return memoryLanguageRepository;
    }

    @Provides
    @Singleton
    AppPreferences provideAppPreferences(SharedAppPreferences sharedAppPreferences) {
        return sharedAppPreferences;
    }

}
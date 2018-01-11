package com.nz2dev.wordtrainer.data.core;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.nz2dev.wordtrainer.data.core.dao.AccountDao;
import com.nz2dev.wordtrainer.data.core.dao.AccountHistoryDao;
import com.nz2dev.wordtrainer.data.core.dao.CourseDao;
import com.nz2dev.wordtrainer.data.core.dao.TrainingDao;
import com.nz2dev.wordtrainer.data.core.dao.WordDao;
import com.nz2dev.wordtrainer.data.core.entity.AccountEntity;
import com.nz2dev.wordtrainer.data.core.entity.AccountHistoryEntity;
import com.nz2dev.wordtrainer.data.core.entity.CourseEntity;
import com.nz2dev.wordtrainer.data.core.entity.TrainingEntity;
import com.nz2dev.wordtrainer.data.core.entity.WordEntity;

/**
 * Created by nz2Dev on 28.11.2017
 */
@Database(entities = {
        AccountEntity.class,
        AccountHistoryEntity.class,
        CourseEntity.class,
        TrainingEntity.class,
        WordEntity.class
}, version = 13)
public abstract class WordTrainerDatabase extends RoomDatabase {

    public abstract AccountDao getAccountDao();
    public abstract AccountHistoryDao getAccountHistoryDao();
    public abstract CourseDao getCourseDao();
    public abstract TrainingDao getTrainingDao();
    public abstract WordDao getWordDao();

}

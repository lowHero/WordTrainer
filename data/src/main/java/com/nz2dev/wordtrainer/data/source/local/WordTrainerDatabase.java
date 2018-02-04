package com.nz2dev.wordtrainer.data.source.local;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.nz2dev.wordtrainer.data.source.local.dao.AccountDao;
import com.nz2dev.wordtrainer.data.source.local.dao.AccountHistoryDao;
import com.nz2dev.wordtrainer.data.source.local.dao.CourseDao;
import com.nz2dev.wordtrainer.data.source.local.dao.TrainingDao;
import com.nz2dev.wordtrainer.data.source.local.dao.WordDao;
import com.nz2dev.wordtrainer.data.source.local.entity.AccountEntity;
import com.nz2dev.wordtrainer.data.source.local.entity.AccountHistoryEntity;
import com.nz2dev.wordtrainer.data.source.local.entity.CourseEntity;
import com.nz2dev.wordtrainer.data.source.local.entity.TrainingEntity;
import com.nz2dev.wordtrainer.data.source.local.entity.WordEntity;

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

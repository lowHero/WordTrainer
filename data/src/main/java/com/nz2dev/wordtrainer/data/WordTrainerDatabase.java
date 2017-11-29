package com.nz2dev.wordtrainer.data;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.nz2dev.wordtrainer.data.dao.AccountDao;
import com.nz2dev.wordtrainer.data.entity.Account;

/**
 * Created by nz2Dev on 28.11.2017
 */
@Database(entities = {Account.class}, version = 1)
public abstract class WordTrainerDatabase extends RoomDatabase {

    public abstract AccountDao accountDao();

}
